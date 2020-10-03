package com.study.course4.bpid.controller;

import com.study.course4.bpid.crypt.PermutationCrypt;
import com.study.course4.bpid.dto.PermutationDecodeRequestDto;
import com.study.course4.bpid.dto.PermutationEncodeRequestDto;
import javafx.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lab2")
public class Lab2Controller {
    @PostMapping("/permutation/encode")
    public ResponseEntity permutationEncode(@RequestBody PermutationEncodeRequestDto request){
        Pair<String, String> keyValue = PermutationCrypt.encode(request.getStringToEncode());
        Map<Object, Object> response = new HashMap<>();
        response.put("key", keyValue.getKey());
        response.put("encodedString", keyValue.getValue());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/permutation/decode")
    public ResponseEntity permutationDecode(@RequestBody PermutationDecodeRequestDto request){
        Map<Object, Object> response = new HashMap<>();
        String decodedString = PermutationCrypt.decode(new Pair<>(request.getKey(), request.getEncodedString()));
        response.put("decodedString", decodedString);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/feistcrypt/encode/file")
    public ResponseEntity feistCryptEncodeFile(@RequestBody File file){
        System.out.println(file.getName());
        return ResponseEntity.ok("123");
    }
}
