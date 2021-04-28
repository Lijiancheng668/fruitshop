package com.greenleaf.fruitshop.model.service;

import com.greenleaf.fruitshop.model.entity.Notice;

import java.util.List;

public interface INoticeService {
    List<Notice> findAllNotices();
}
