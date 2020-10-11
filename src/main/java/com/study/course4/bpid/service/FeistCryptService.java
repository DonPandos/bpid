package com.study.course4.bpid.service;

import com.study.course4.bpid.crypt.FeistCrypt;
import javafx.util.Pair;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class FeistCryptService {

    public Pair<String, String> encode(String stringToEncode, Integer roundsCount){
        Pair<String, List<String>> encodedPair = FeistCrypt.encode(new StringBuilder(stringToEncode), roundsCount);
        String keys = "";
        for(String key : encodedPair.getValue()) keys += (key + ",");
        return new Pair<>(encodedPair.getKey(), keys);
    }

    public String decode(String encodedString, String keys){
        System.out.println("encodedString: " + encodedString);
        System.out.println("keys: " + keys);
        List<String> keysList = Arrays.asList(keys.split(","));
        String decodedString = FeistCrypt.decode(encodedString, keysList);
        return decodedString;
    }

}
