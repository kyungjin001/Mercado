package com.icia.gangcamping.controller;


import com.icia.gangcamping.dto.*;
import com.icia.gangcamping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.icia.gangcamping.common.SessionConst.LOGIN_EMAIL;



@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService ms;

//    @GetMapping("save")
//    public String saveForm(Model model) {
//        model.addAttribute("member", new MemberSaveDTO());
//        return "member/save";
//    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO) {

            Long memberId = ms.save(memberSaveDTO);
        return "index";
    }


//    @PostMapping("save")
//    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult) throws IOException {
//        if(bindingResult.hasErrors()) {
//            return "member/save";
//        }
//        try {
//            Long memberId = ms.save(memberSaveDTO);
//        } catch (IllegalStateException e) {
//            bindingResult.reject("emailCheck", e.getMessage());
//            return "member/save";
//        }
//        return "redirect:/member/login";
//    }

//    @GetMapping("/login")
//    public String loginForm(Model model) {
//        model.addAttribute("login", new MemberLoginDTO());
//        return "login_!";
//    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, HttpSession session){

        boolean loginResult = ms.login(memberLoginDTO);

        if(ms.login(memberLoginDTO)){
            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            return "redirect:/";
        } else {
            return "redirect:/";

        }
    }

    // 이메일 중복 체크
    @PostMapping("/emailDp")
    @ResponseBody
    public String emailDp(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("emailDP() :" + memberEmail);
        String result = ms.emailDp(memberEmail);
        return result;
    }


    //마이페이지
    @GetMapping("/mypage")
    public String mypage() {
        return "member/mypage";
    }

    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/mypage";

    }



    @GetMapping("/update")
    public String updateForm(Model model, HttpSession session) {
        System.out.println(session.getAttribute("loginEmail"));
        String memberEmail = (String) session.getAttribute("loginEmail");
        MemberDetailDTO member = ms.findByEmail(memberEmail);
        model.addAttribute("member", member);
        System.out.println(member);
        return "member/update";
    }

//    @PostMapping("/updateAddr")
//    public String updateAddr(@RequestParam String memberAddr, MemberUpdateAddrDTO memberUpdateAddrDTO){
//        Long memberId = ms.updateAddr(memberUpdateAddrDTO);
//        return "member/update";
//    }

//    @PostMapping("/update")
//    public String update(@ModelAttribute MemberUpdateDTO memberUpdateDTO) {
//        Long memberId = ms.update(memberUpdateDTO);
//        return "member/update";
//        return "redirect:/member/" + memberUpdateDTO.getMemberId();
//    }


    @PutMapping("/{memberId}")
    public ResponseEntity update(@ModelAttribute MemberUpdateDTO memberUpdateDTO) throws IllegalStateException, IOException{
        System.out.println(memberUpdateDTO);
        Long memberId = ms.update(memberUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/confirmPW")
    public String confirmPWForm(Model model, HttpSession session) {
        System.out.println(session.getAttribute("loginEmail"));
        String memberEmail = (String) session.getAttribute("loginEmail");
        MemberDetailDTO member = ms.findByEmail(memberEmail);
        model.addAttribute("member", member);
        System.out.println(member);
        return "member/confirmPW";
    }

//    @PutMapping("/{memberId}")
//    public ResponseEntity confirmPW(@ModelAttribute MemberUpdateDTO memberUpdateDTO) throws IllegalStateException, IOException{
//        System.out.println(memberUpdateDTO);
//        Long memberId = ms.update(memberUpdateDTO);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @DeleteMapping("/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId){
        ms.deleteById(memberId);
        return "index";
    }

//    @DeleteMapping("/{memberId}")
//    public ResponseEntity deleteById(@PathVariable("memberId") Long memberId){
//        ms.deleteById(memberId);
//        return new ResponseEntity(HttpStatus.OK);
//    }


    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/bookList")
    public String bookList() {


        return "member/bookList";
    }


    @GetMapping("/delete")
    public String delete() {
        return "member/delete";

    }

    @GetMapping("/shoppingDetail")
    public String shoppingDetail() {
        return "member/shoppingDetail";

    }


    @GetMapping("/bookDetail")
    public String bookDetail() {
        return "member/bookDetail";

    }

    @GetMapping("/addrChange")
    public String addrChange() {
        return "member/addrChange";

    }


    @GetMapping("/shoppingList")
    public String shoppingList() {
        return "member/shoppingList";
    }


    @GetMapping("/shoppingLike")
    public String shoppingLike() {
        return "member/shoppingLike";
    }


}
