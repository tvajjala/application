package com.tvajjala.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.tvajjala.persistence.model.OnlineOrder;
import com.tvajjala.persistence.model.OrderLineItem;

public class OrderUtil {
    /**
     * generates orderNumber
     *
     * @return orderNumber
     */
    public static String getOrderNumber() {
        final SimpleDateFormat sdf = new SimpleDateFormat( "YYYYMMMDDHHmms" );
        return "OD-" + sdf.format( new Date() ).toUpperCase();
    }

    public static void calculatePrices( OnlineOrder onlineOrder ) {

        BigDecimal total = new BigDecimal( 0.0 );
        BigDecimal taxes = new BigDecimal( 0.0 );

        for ( final OrderLineItem lineItem : onlineOrder.getLineItems() ) {
            total = total.add( new BigDecimal( lineItem.getLineTotalPrice() ).multiply( new BigDecimal( lineItem.getQuantity() ) ) );
            taxes = taxes.add( new BigDecimal( lineItem.getTax() ) );
        }

        onlineOrder.setTotalPrice( total.setScale( 4, RoundingMode.HALF_UP ).doubleValue() );
        onlineOrder.setTax( taxes.setScale( 4, RoundingMode.HALF_UP ).doubleValue() );

        final Double subTotal = onlineOrder.getTotalPrice() + onlineOrder.getTax() + onlineOrder.getAdditionalFee() + onlineOrder.getShippingCost()
                + onlineOrder.getDiscountValue();

        onlineOrder.setSubTotal( new BigDecimal( subTotal ).setScale( 4, RoundingMode.HALF_UP ).doubleValue() );

    }

    public static String getPhoneNumber() {

        final Random rand = new Random();
        int num1, num2, num3;

        num1 = rand.nextInt( 900 ) + 100;
        num2 = rand.nextInt( 643 ) + 100;
        num3 = rand.nextInt( 9000 ) + 1000;

        return num1 + "-" + num2 + "-" + num3;
    }

    public static String getName() {

        final String[] names =
                { "Ram", "Jill", "Tom", "Brandon", "Kiran", "Karthi", "Bhanu", "Kevin", "Techkin", "Prashanth", "Ranga", "Arun", "John" };
        final int index = new Random().nextInt( names.length );
        final String name = names[index];
        return name;
    }

}
