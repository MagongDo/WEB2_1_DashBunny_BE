package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.controller;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AddNotice;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.NoticeListView;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.NoticeView;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.UpdateNotice;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {
    private final NoticeService noticeService;
    //private final UserService userService;

    //공지사항 등록 api (POST)
    @PostMapping("/admin")
    public ResponseEntity<Notice> addNotice(@RequestBody AddNotice request) {
        Notice saveNotice = noticeService.saveNotice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveNotice);
    }

    //공지사항 목록 조회 api (GET)
    //현재 로그인한 사용자의 권한을 내부에서 확인하고 필터링해야함
    //http://localhost:8080/api/notice/admin?role=OWNER이게 아님..
    //@PreAuthorize("hasRole('ROLE_ADMIN') or #role == authentication.principal.role")
    @GetMapping("")
    public ResponseEntity<List<NoticeListView>> getNotices(@RequestParam String role)  {
        List<NoticeListView> notices;
        if(!role.equals("admin")) { //사장님, 사용자가 조회
            notices=noticeService.getAllNoticesByRole(role);
        }else{ //관리자 조회
            notices=noticeService.getAllNotices();
        }
        return ResponseEntity.ok().body(notices);
    }

//    @GetMapping()
//    public ResponseEntity<List<NoticeListView>> getNotices() {
//        List<NoticeListView> notices=noticeService.getAllNotices();
//        return ResponseEntity.ok().body(notices);
//    }

    //특정 공지사항 조회 api (GET)
    @GetMapping("/id/{noticeId}")
    public ResponseEntity<NoticeView> getNotice(@PathVariable Long noticeId) {
        NoticeView noticeView=noticeService.getNotice(noticeId);
        return ResponseEntity.ok().body(noticeView);
    }

    //공지사항 삭제 api (DELETE)
    @DeleteMapping("/admin/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        Map<String,String> response=new HashMap<>();
        response.put("message","NoticeId: "+noticeId+" deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //공지사항 수정 api (PUT)
    @PutMapping("/admin/{noticeId}")
    public ResponseEntity<UpdateNotice> updateNotice(@PathVariable Long noticeId, @RequestBody AddNotice request) {
        UpdateNotice updateNotice=noticeService.updateNotice(noticeId, request);
        return ResponseEntity.ok().body(updateNotice);
    }
}
