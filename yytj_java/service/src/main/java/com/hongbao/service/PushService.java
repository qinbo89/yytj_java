package com.hongbao.service;

import java.util.Map;

public interface PushService {
    public void pushToUser(Long userId, String message, Map<String, String> extras);

    public void pushToTag(String tag, String message, Map<String, String> extras);
}
