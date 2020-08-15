package com.myRetail.util;

import java.util.UUID;

public class ProductUtil {
    public static UUID formatUuid(String productId) {
        productId = productId.replace("-", "");
        String formatted = String.format(
                productId.substring(0, 8) + "-" +
                        productId.substring(8, 12) + "-" +
                        productId.substring(12, 16) + "-" +
                        productId.substring(16, 20) + "-" +
                        productId.substring(20, 32)
        );
        return UUID.fromString(formatted);
    }
}
