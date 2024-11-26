package com.devcourse.web2_1_dashbunny_be.config.s3;

import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {

  private final FileUploadService fileUploadService;
  private final StoreManagementService storeManagementService;
  private final MenuService menuService;

  /**
  * 파일 업로드 API
  * @param menuImageFile 업로드할 파일
  * @return 업로드된 파일의 URL.
  */
  @PatchMapping("/{menuId}")
  public ResponseEntity<String> uploadMenuImageFile(@RequestParam("menuImageFile") MultipartFile menuImageFile,
                                           @PathVariable("menuId") Long menuId) {
    try {
      String fileUrl = fileUploadService.uploadFile(menuImageFile, "menuImage");
      menuService.updateMenuImage(menuId, fileUrl);
      return ResponseEntity.ok("메뉴 이미지가 성공적으로 업데이트되었습니다.");
    } catch (MultipartException e) {
      return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

}
