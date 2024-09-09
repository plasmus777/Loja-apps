package com.github.plasmus777.service;

import java.util.List;

public interface Service<T1, T2>{
    boolean save(T1 t);
    boolean update(T1 t1, T1 t2);
    boolean delete(T1 t);
    T1 search(long id);
    T1 searchExact(String search, T2 obj);
    List<T1> search(String search);
    void listAll();
    List<T1> getList();
}
