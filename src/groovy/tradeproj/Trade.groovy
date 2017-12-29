package tradeproj

import groovy.transform.AutoClone

/**
 * Created by bkishore on 12/29/2017.
 */
@AutoClone
class Trade {
    def tradeDate
    def tradeTime
    def exchange
    def symbol
    def type
    int qty
    def rate
    def orderNo
    def tradeNo
    def comment
    def avgPrice
    def netQuantity
    def cumBuyPrice


    @Override
    public String toString() {
        return "Trade{" +
                "tradeDate=" + tradeDate +
                ", tradeTime=" + tradeTime +
                ", exchange=" + exchange +
                ", symbol=" + symbol +
                ", type=" + type +
                ", qty=" + qty +
                ", rate=" + rate +
                ", orderNo=" + orderNo +
                ", tradeNo=" + tradeNo +
                ", comment=" + comment +
                ", avgPrice=" + avgPrice +
                '}';
    }

    public enum TRADETYPE {

        BUY ("B"),
        SELL ("S"),

        private String type;

        private TRADETYPE(String type) {
            this.type = type;
        }

        public String toString() {
            return type;
        }
    }
}
