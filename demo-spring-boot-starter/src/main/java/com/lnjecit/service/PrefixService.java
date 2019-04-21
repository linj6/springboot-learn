package com.lnjecit.service;

/**
 * @author lnj
 * @description 前缀
 * @date 2019-04-21 17:37
 **/
public class PrefixService {
    private String prefix;

    public PrefixService(String prefix) {
        this.prefix = prefix;
    }

    public String wrap(String word) {
        return prefix + word;
    }
}
