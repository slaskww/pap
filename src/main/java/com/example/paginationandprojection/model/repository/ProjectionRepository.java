package com.example.paginationandprojection.model.repository;

import com.example.paginationandprojection.model.entity.projections.Projection;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Interfejs obsługujący podstawowe operacje na projekcjach.
 *
 * @param <TypIdEncji> Na potrzeby projekcji do pojedynczej encji
 */

public interface ProjectionRepository<TypIdEncji extends Serializable> {

        /**
         * Pobieranie listy encji z wynikami ograniczonymi do danej projekcji
         * @param projectionClass interfejs projekcji
         * @param <P> typ reprezetujący projekcje
         * @return lista wyników ograniczona do danej projekcji
         */
        <P extends Projection> List<P> findAllBy(Class<P> projectionClass);

        /**
         * Pobieranie listy encji z wynikami ograniczonymi do danej projekcji
         * @param projectionClass interfejs projekcji
         * @param pageable konfiguracja stronicowania
         * @param <P> typ reprezetujący projekcje
         * @return lista wyników ograniczona do danej projekcji
         */
        <P extends Projection> List<P> findAllBy(Class<P> projectionClass, Pageable pageable);

        /**
         * Pobieranie pojedynczje encji z wynikami ograniczonymi do danej projekcji
         * @param id identyfikator encji
         * @param projectionClass interfejs projekcji
         * @param <P> typ reprezentujący projekcie
         * @return wynik ograniczony do danej projekcji
         */
        <P extends Projection> P findById(TypIdEncji id, Class<P> projectionClass);
}
