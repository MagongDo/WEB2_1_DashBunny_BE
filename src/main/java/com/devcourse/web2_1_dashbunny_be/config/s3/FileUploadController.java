package com.devcourse.web2_1_dashbunny_be.config.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {

  private final fileUploadService fileUploadService;
  private final MenuService menuService;
  /**
  * 파일 업로드 API
  * @param file 업로드할 파일
  * @return 업로드된 파일의 URL.
  */
  @PatchMapping("/{menuId}")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                           @PathVariable("menuId") Long menuId) {
    try {
      String fileUrl = fileUploadService.uploadFile(file, "menuImage");
      menuService.updateMenuImage(menuId, fileUrl);
      return ResponseEntity.ok("메뉴 이미지가 성공적으로 업데이트되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
    }
    }
}
