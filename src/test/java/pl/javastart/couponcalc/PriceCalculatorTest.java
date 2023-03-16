package pl.javastart.couponcalc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {
    private PriceCalculator priceCalculator = new PriceCalculator();
    @Test
    public void shouldReturnZeroForNoProducts() {
        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result).isEqualTo(0.);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceFor4ProductsAnd4Coupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Krzesło", 5.99, Category.HOME));
        products.add(new Product("Przęsło", 5.99, Category.CAR));
        products.add(new Product("coś co pasuje, ale się nie rymuje", 5.99, Category.ENTERTAINMENT));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));
        coupons.add(new Coupon(Category.HOME, 20));
        coupons.add(new Coupon(Category.CAR, 20));
        coupons.add(new Coupon(Category.ENTERTAINMENT, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(22.76);
    }

    @Test
    public void shouldReturnPriceAndUseOnlyOneMoreBeneficialDiscount() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Krzesło", 5.99, Category.HOME));
        products.add(new Product("Przęsło", 5.99, Category.CAR));
        products.add(new Product("coś co pasuje, ale się nie rymuje", 5.99, Category.ENTERTAINMENT));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 10));
        coupons.add(new Coupon(Category.HOME, 10));
        coupons.add(new Coupon(Category.CAR, 10));
        coupons.add(new Coupon(Category.ENTERTAINMENT, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(22.76);
    }

    @Test
    public void shouldCountPriceFor4ProductsAnd2Coupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 55.99, Category.FOOD));
        products.add(new Product("Krzesło", 55.99, Category.HOME));
        products.add(new Product("Przęsło", 55.99, Category.CAR));
        products.add(new Product("coś co pasuje, ale się nie rymuje", 55.99, Category.ENTERTAINMENT));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.CAR, 20));
        coupons.add(new Coupon(Category.ENTERTAINMENT, 40));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(201.56);
    }
}