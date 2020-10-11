package com.study.course4.bpid.controller;

import com.study.course4.bpid.crypt.PermutationCrypt;
import com.study.course4.bpid.dto.PermutationDecodeRequestDto;
import com.study.course4.bpid.dto.PermutationEncodeRequestDto;
import com.study.course4.bpid.service.FeistCryptService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab2")
public class Lab2Controller {

    @Autowired
    FeistCryptService feistCryptService;

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
    public ResponseEntity feistCryptEncodeFile(@RequestParam("file") MultipartFile file, @RequestParam("roundsCount") Integer roundsCount){
        try {
            Pair<String, String> encodedPair = feistCryptService.encode(new String(file.getBytes()), roundsCount);
            System.out.println(new String(file.getBytes()));
            Map<Object, Object> response = new HashMap<>();
            response.put("encodedString", encodedPair.getKey());
            response.put("keys", encodedPair.getValue());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

        @PostMapping("/feistcrypt/decode/file")
    public ResponseEntity feistCryptDecodeFile(@RequestParam("encodedStringFile") MultipartFile encodedStringFile, @RequestParam("keysFile") MultipartFile keysFile){
        try{
            String decodedString = feistCryptService.decode(new String(encodedStringFile.getBytes()), new String(keysFile.getBytes()));
            Map<Object, Object> response = new HashMap<>();
            response.put("decodedString", new String(decodedString.getBytes("UTF-8")));
//            File file = new File("src/main/java/com/study/course4/bpid/files/test.txt");
//            FileWriter fw = new FileWriter(file);
//            fw.write(decodedString);
//            fw.close();
            return ResponseEntity.ok(response);
        } catch (IOException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
