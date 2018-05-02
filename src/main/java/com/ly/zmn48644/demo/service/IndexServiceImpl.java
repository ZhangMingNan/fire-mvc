package com.ly.zmn48644.demo.service;

import com.ly.zmn48644.firemvc.annotation.Service;

import java.util.Date;

@Service
public class IndexServiceImpl implements IndexService {
    @Override
    public Date localTime() {
        return new Date();
    }
}
