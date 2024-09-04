package com.github.plasmus777.service;

import java.util.List;

public interface Service<T>{
    boolean save(T t);
    boolean update(T t1, T t2);
    boolean delete(T t);
    T search(long id);
    List<T> search(String search);
    void listAll();
}
