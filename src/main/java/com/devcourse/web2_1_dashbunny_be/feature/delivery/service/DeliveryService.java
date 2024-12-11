package com.devcourse.web2_1_dashbunny_be.feature.delivery.service;

import com.devcourse.web2_1_dashbunny_be.config.GeoUtils;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.DeliveryHistory;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.DeliveryRequests;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryProgressStatus;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryRequestStatus;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryWorkerStatus;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.dto.DeliveryOrderNotificationDto;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.dto.DeliveryRequestsDto;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.dto.DeliveryWorkerUpdateAddressRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.repository.DeliveryHistoryRepository;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.repository.DeliveryRequestsRepository;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.util.RandomStringService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersStoreService;
import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

	private final UserService userService;
	private final UsersStoreService usersStoreService;
	private final UserRepository userRepository;
	private final StoreManagementRepository storeManagementRepository;
	private final DeliveryRequestsRepository deliveryRequestsRepository;
	private final RandomStringService randomStringService;
	private final DeliveryHistoryRepository deliveryHistoryRepository;

	// 주소를 통해 현재위치 변경
	public User updateDeliveryWorkerAddress(
					DeliveryWorkerUpdateAddressRequestDto deliveryWorkerUpdateAddressRequestDto,
					User currentUser)
	{
		JsonObject addressLatLon = usersStoreService.getUserAdressLatLon(deliveryWorkerUpdateAddressRequestDto.getAddress());

		// 사용자의 위도와 경도 추출
		double userLatitude = addressLatLon.get("latitude").getAsDouble();
		double userLongitude = addressLatLon.get("longitude").getAsDouble();

		// 위도 경도 변경
		User setUserName = currentUser.toBuilder()
						.latitude(userLatitude)
						.longitude(userLongitude)
						.build();
		return userRepository.save(setUserName);
	}
	// 배달원 상태 토글 변경
	public User toggleDeliveryStatus(User currentUser) {

		// 현재 상태를 반대로 전환(READY 일시 NOT_READY)
		DeliveryWorkerStatus newStatus =
						currentUser.getDeliveryStatus() == DeliveryWorkerStatus.READY ? DeliveryWorkerStatus.NOT_READY : DeliveryWorkerStatus.READY;
		// 배달 상태 변경
		currentUser = currentUser.toBuilder()
						.deliveryStatus(newStatus)
						.build();
		return userRepository.save(currentUser);
	}

	// 배달원 상태 토글 변경
	public User statusChangeNotReadyDeliveryWorker(String authorizationHeader) {
		User currentUser = userService.getCurrentUser(authorizationHeader);

		// 배달 상태 변경
		currentUser = currentUser.toBuilder()
						.deliveryStatus(DeliveryWorkerStatus.NOT_READY)
						.build();
		return userRepository.save(currentUser);
	}

	/**
	 * 가게 반경 내 배달 가능한 배달원 찾기
	 * @param deliveryRequests 저장된 배달 요청 가져오기
	 * @return 조건에 맞는 배달원 목록
	 */
	public List<User> deliveryWorkerWithInARadius(DeliveryRequests deliveryRequests) {

		StoreManagement storeManagement = storeManagementRepository.findByStoreId(deliveryRequests.getStoreId());
		if (storeManagement == null) {
			throw new IllegalArgumentException("storeId에 대한 스토어를 찾을 수 없습니다: " + deliveryRequests.getStoreId());
		}

		double storeLatitude = storeManagement.getLatitude();
		double storeLongitude = storeManagement.getLongitude();
		double allowedRadius = 3.0; // 허용 반경 (킬로미터 단위)
		log.info("체크 가게 좌표 : {}, {}", storeLatitude, storeLongitude);
		// 배달원 리턴
		List<User> allowedWorker =
						userRepository.findAvailableDeliveryUsersWithinRadius(storeLatitude, storeLongitude, allowedRadius);
		return allowedWorker;
	}

	public DeliveryRequests saveDeliveryRequests (DeliveryRequestsDto deliveryRequestsDto) {

		String storeId = deliveryRequestsDto.getStoreId();
		StoreManagement store = storeManagementRepository.findById(storeId)
						.orElseThrow(() -> new IllegalArgumentException("storeId에 대한 스토어를 찾을 수 없습니다: " + storeId));

		// 배달주소의 위도와 경도 추출
		JsonObject addressLatLon = usersStoreService.getUserAdressLatLon(deliveryRequestsDto.getDeliveryAddress());
		double deliveryLatitude = addressLatLon.get("latitude").getAsDouble();
		double deliveryLongitude = addressLatLon.get("longitude").getAsDouble();

		// 가게, 배달주소 사이의 좌표(km 단위)
		double distance =
						GeoUtils.getUsersWithinRadius(store.getLatitude(), store.getLongitude(), deliveryLatitude, deliveryLongitude);

		DeliveryRequests deliveryRequests = DeliveryRequests.builder()
						.storeId(storeId)
						.orderId(deliveryRequestsDto.getOrderId())
						.deliveryAddress(deliveryRequestsDto.getDeliveryAddress())
						.deliveryDetailsAddress(deliveryRequestsDto.getDeliveryDetailsAddress())
						.deliveryWorkerNote(deliveryRequestsDto.getDeliveryWorkerNote())
						.preparationTime(deliveryRequestsDto.getPreparationTime())
						.orderDate(deliveryRequestsDto.getOrderDate())
						.deliveryPrice(deliveryRequestsDto.getDeliveryPrice())
						.distance(distance)
						.build();
		return deliveryRequestsRepository.save(deliveryRequests);
	}

	public DeliveryOrderNotificationDto convertToDeliveryOrderNotificationDto (DeliveryRequests deliveryRequests) {
		StoreManagement storeManagement = storeManagementRepository.findById(deliveryRequests.getStoreId())
						.orElseThrow(() -> new IllegalArgumentException("storeId에 대한 스토어를 찾을 수 없습니다: " + deliveryRequests.getStoreId()));
		String storeAddress = storeManagement.getAddress();
		log.info("convertToDeliveryOrderNotificationDto getStoreId : {}",deliveryRequests.getStoreId());
		log.info("convertToDeliveryOrderNotificationDto storeAddress : {}",storeAddress);
		return DeliveryOrderNotificationDto.builder()
						.deliveryRequestId(deliveryRequests.getDeliveryRequestId())
						.storeId(deliveryRequests.getStoreId())
						.storeName(storeManagement.getStoreName())
						.storeAddress(storeAddress)
						.preparationTime(deliveryRequests.getPreparationTime())
						.distance(deliveryRequests.getDistance())
						.deliveryPrice(deliveryRequests.getDeliveryPrice())
						.deliveryAddress(deliveryRequests.getDeliveryAddress())
						.deliveryDetailsAddress(deliveryRequests.getDeliveryDetailsAddress())
						.deliveryWorkerNote(deliveryRequests.getDeliveryWorkerNote())
						.build();
	}

	// 배차 가져오기 선착순 1명 (상태변경)
	public DeliveryHistory acceptDeliveryRequest (String authorizationHeader, DeliveryRequests deliveryRequests) {

		User user = userService.getCurrentUser(authorizationHeader);
		DeliveryRequestStatus status =
						deliveryRequestsRepository.findStatusByDeliveryRequestId(deliveryRequests.getDeliveryRequestId());
		log.info("배달 요청 상태 바뀌기 전 : {}", status);

		if(status != DeliveryRequestStatus.REQUESTED){
			throw new IllegalArgumentException("status != REQUESTED");
		}
		// Pessimistic Lock을 사용하여 DeliveryRequests 엔티티 조회
		DeliveryRequests currentDeliveryRequests = deliveryRequestsRepository.findByDeliveryRequestIdForUpdate(deliveryRequests.getDeliveryRequestId());

		DeliveryRequests updatedRequest = currentDeliveryRequests.toBuilder()
						.status(DeliveryRequestStatus.ASSIGNED)
						.build();
		log.info("if 문 속 DeliveryRequests : {}", updatedRequest);
		deliveryRequestsRepository.saveAndFlush(updatedRequest);

		log.info("배달 요청 상태 바뀐 후 : {}", deliveryRequestsRepository.findStatusByDeliveryRequestId(deliveryRequests.getDeliveryRequestId()));

		return deliveryHistoryRepository.save(DeliveryHistory.builder()
						.userId(user.getUserId())
						.deliveryRequestId(deliveryRequests.getDeliveryRequestId())
						.deliveryPrice(deliveryRequests.getDeliveryPrice())
						.uniqueCode(randomStringService.generateRandomString())
						.distance(deliveryRequests.getDistance())
						.status(DeliveryProgressStatus.DELIVERING)
						.build());
	}



}
