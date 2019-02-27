package com.yh.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //https://www.cnblogs.com/ritchiewang/p/5767416.html 选择器
        //获取页面
        Document document= Jsoup.connect("https://www.amazon.cn/gp/bestsellers/digital-text")
                //模拟火狐浏览器
                .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                .get();
        Element main = document.getElementById("zg-ordered-list");

        Elements books = main.select("li[class=zg-item-immersion]");
        for(Element book : books){
            //输出href后的值，即主页上每个关注问题的链接
            Element bookDetail = book.selectFirst("div").selectFirst(".zg-item");
            Element img = bookDetail.selectFirst("a").selectFirst("span").selectFirst("div").selectFirst("img");
            String title = img.attr("alt");
            String bookImg = img.attr("src");
            String url = "https://www.amazon.cn" + bookDetail.selectFirst("a").attr("href");

            String author = bookDetail.child(1).select("span").text();
            String star = bookDetail.child(2).selectFirst("a").attr("title");
            String starCount = bookDetail.child(2).select("a:nth-child(2)").text();
            String price = bookDetail.child(4).select(".p13n-sc-price").text();


            Document bookpage = Jsoup.connect(url)
                    //模拟火狐浏览器
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .get();
            //十几次就被检测出是自动程序了
            Element info = bookpage.getElementById("productDetailsTable").selectFirst(".bucket").selectFirst(".content").selectFirst("ul");
            String press = info.select("li:contains(出版社)").text();


            System.out.println("\n"+"书名："+ title
                    +"\n" + "链接："+ url
                    +"\n" + "作者："+ author
                    +"\n" + press
                    +"\n" + "评分："+ star
                    +"\n" + "人数："+ starCount
                    +"\n" + "价格："+ price);
        }
    }
}
