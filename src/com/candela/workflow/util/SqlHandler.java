package com.candela.workflow.util;

import java.util.Map;

/**
 * @Author linbin
 * @Create 2019-11-18-17:19
 */
public interface SqlHandler {

    String create(String table, Map<String,String> map);
    String update(String table, Map<String,String> map);
    String drop(String table, Map<String,String> map);
    String get(String table, Map<String,String> map);
}
