package com.greenleaf.fruitshop.model.service.impl;

import com.greenleaf.fruitshop.model.entity.Notice;
import com.greenleaf.fruitshop.model.mapper.NoticeMapper;
import com.greenleaf.fruitshop.model.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public List<Notice> findAllNotices() {
        return noticeMapper.selectList(null);
    }
}
