package com.github.plasmus777.repository;

import java.util.List;

public interface Database <T>{
    boolean save(T t);
    boolean update(T t1, T t2);
    boolean delete(T t);
    T search(long id);
    List<T> listAll();
}
