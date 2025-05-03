package com.example.fashionshop.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.fashionshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
       @Query("SELECT p FROM Product p " +
       "WHERE p.idCat = :idCat " +
       "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
       "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
       "AND (:occasion IS NULL OR p.occasion = :occasion)")
Page<Product> findByIdCatWithFilters(
        @Param("idCat") Long idCat,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("occasion") String occasion,
        Pageable pageable);

@Query("SELECT p FROM Product p " +
       "WHERE p.idCat = :idCat AND p.idSubcat = :idSubcat " +
       "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
       "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
       "AND (:occasion IS NULL OR p.occasion = :occasion)")
Page<Product> findByIdCatAndIdSubcatWithFilters(
        @Param("idCat") Long idCat,
        @Param("idSubcat") Long idSubcat,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("occasion") String occasion,
        Pageable pageable);
    @Query("SELECT p FROM Product p " +
           "WHERE (:idCat IS NULL OR p.idCat = :idCat) " +
           "AND (:idSubcat IS NULL OR p.idSubcat = :idSubcat) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:occasion IS NULL OR p.occasion = :occasion)")
    Page<Product> findByFilters(
            @Param("idCat") Long idCat,
            @Param("idSubcat") Long idSubcat,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("occasion") String occasion,
            Pageable pageable);

            @Query("SELECT p FROM Product p " +
            "WHERE p.is_sale = true " +
            "AND (:idCat IS NULL OR p.idCat = :idCat) " +
            "AND (:idSubcat IS NULL OR p.idSubcat = :idSubcat) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:occasion IS NULL OR p.occasion = :occasion)")
     Page<Product> findSaleByFilters(
             @Param("idCat") Long idCat,
             @Param("idSubcat") Long idSubcat,
             @Param("minPrice") Double minPrice,
             @Param("maxPrice") Double maxPrice,
             @Param("occasion") String occasion,
             Pageable pageable);
 
     @Query("SELECT p FROM Product p " +
            "WHERE p.is_new = true " +
            "AND (:idCat IS NULL OR p.idCat = :idCat) " +
            "AND (:idSubcat IS NULL OR p.idSubcat = :idSubcat) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:occasion IS NULL OR p.occasion = :occasion)")
     Page<Product> findNewByFilters(
             @Param("idCat") Long idCat,
             @Param("idSubcat") Long idSubcat,
             @Param("minPrice") Double minPrice,
             @Param("maxPrice") Double maxPrice,
             @Param("occasion") String occasion,
             Pageable pageable);
}
