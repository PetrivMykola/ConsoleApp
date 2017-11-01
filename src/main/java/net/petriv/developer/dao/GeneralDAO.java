package main.java.net.petriv.developer.dao;

import java.util.List;

public interface GeneralDAO<T> {

        void save(T v);

        T getById(int id);

        List<T> getAll();

        void delete(int id);

        void update (T v);

    }


