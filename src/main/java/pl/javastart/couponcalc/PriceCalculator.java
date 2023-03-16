package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {
    private static final double DIVIDER_TO_DECIMAL_VALUE = 100;

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        if (products == null) {
            return 0;
        }
        if (coupons == null || !isMatchedAnyCouponsCategoryToProductsCategory(products, coupons)) {
            return countPriceWithoutMatchedCoupons(products);
        }
        double resultPrice = Double.MAX_VALUE;
        for (Coupon coupon : coupons) {
            double tempPrice = 0;
            for (Product product : products) {
                if (product.getCategory().name().equals(coupon.getCategory().name())) {
                    double discount = coupon.getDiscountValueInPercents() / DIVIDER_TO_DECIMAL_VALUE;
                    tempPrice += product.getPrice() - (product.getPrice() * discount);
                } else {
                    tempPrice += product.getPrice();
                }
            }
            resultPrice = Math.min(resultPrice, tempPrice);
        }
        return round(resultPrice);
    }

    private boolean isMatchedAnyCouponsCategoryToProductsCategory(List<Product> products, List<Coupon> coupons) {
        return products.stream()
                .map(Product::getCategory)
                .map(Enum::name)
                .anyMatch(prodcuctCategoryName -> coupons.stream()
                        .map(Coupon::getCategory)
                        .map(Enum::name)
                        .anyMatch(prodcuctCategoryName::equals));
    }

    private double countPriceWithoutMatchedCoupons(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce((Double::sum))
                .orElse(0.0);
    }

    public static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    // TODO
}
