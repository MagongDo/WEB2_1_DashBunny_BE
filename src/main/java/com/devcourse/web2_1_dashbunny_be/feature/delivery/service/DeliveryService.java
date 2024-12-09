package com.devcourse.web2_1_dashbunny_be.feature.delivery.service;

import com.devcourse.web2_1_dashbunny_be.config.GeoUtils;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.DeliveryRequests;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryWorkerStatus;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.dto.DeliveryRequestsDto;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.dto.DeliveryWorkerUpdateAddressRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.repository.DeliveryRequestsRepository;
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

	/**
	 * 배달 가능한 배달원 찾기
	 * @param
	 * @return 조건에 맞는 배달원 목록
	 */
//	public List<User> deliveryWorkerWithinARadius(DeliveryRequests deliveryRequests) {
//
//		StoreManagement storeManagement = storeManagementRepository.findByStoreId(order.getStoreId());
//
//		Double storeLatitude = storeManagement.getLatitude();
//		Double storeLongitude = storeManagement.getLongitude();
//		Double allowedRadius = 5.0; // 허용 반경 (킬로미터 단위)
//
//		List<User> allowedWorker = userRepository.findByDeliveryStatusAndRoleAndLatitudeAndLongitude(
//						DeliveryWorkerStatus.READY,
//						storeLatitude - allowedRadius,
//						storeLatitude + allowedRadius,
//						storeLongitude - allowedRadius,
//						storeLongitude + allowedRadius
//		);
//		return allowedWorker;
//	}

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
						.deliveryAddress(deliveryRequestsDto.getDeliveryAddress())
						.deliveryDetailsAddress(deliveryRequestsDto.getDeliveryDetailsAddress())
						.driverRequest(deliveryRequestsDto.getDriverRequest())
						.distance(distance)
						.build();
		return deliveryRequestsRepository.save(deliveryRequests);
	}

}
