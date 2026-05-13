package com.ylguohe.admin.config;

import com.ylguohe.admin.entity.*;
import com.ylguohe.admin.entity.enums.*;
import com.ylguohe.admin.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final VehicleDestinationRepository vehicleDestinationRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final HeroBannerRepository heroBannerRepository;
    private final StatisticRepository statisticRepository;
    private final CompanyTimelineRepository companyTimelineRepository;
    private final QualificationRepository qualificationRepository;
    private final LogisticsRouteRepository logisticsRouteRepository;
    private final WarehouseRepository warehouseRepository;
    private final ContactInfoRepository contactInfoRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            log.info("Database already initialized, skipping seed data.");
            return;
        }
        log.info("Initializing database with seed data...");
        initHeroBanners();
        initStatistics();
        initProducts();
        initVehicleDestinations();
        initNewsArticles();
        initTimeline();
        initQualifications();
        initLogisticsRoutes();
        initWarehouses();
        initContactInfo();
        log.info("Seed data initialization complete.");
    }

    private void initHeroBanners() {
        heroBannerRepository.save(HeroBanner.builder()
                .tag("2025\u5e74\u8fdb\u51fa\u53e3\u8d38\u6613\u989d 5.79 \u4ebf\u5143")
                .title("\u4e1d\u8def\u901a\u8fbe \u00b7 \u679c\u9999\u4e16\u754c")
                .description("2025\u5e74\u8fdb\u51fa\u53e3\u8d38\u6613\u989d\u540c\u6bd4\u589e\u957f1,118%\n\u5360\u6768\u51cc\u793a\u8303\u533a\u5916\u8d38\u603b\u989d39%\uff0c\u8fde\u63a5\u5168\u740320+\u56fd\u5bb6")
                .ctaText("\u63a2\u7d22\u4ea7\u54c1").ctaLink("products.html")
                .ctaText2("\u4e86\u89e3\u6211\u4eec").ctaLink2("about.html")
                .sortOrder(1).active(true).build());

        heroBannerRepository.save(HeroBanner.builder()
                .tag("\u4e2d\u6b27\u73ed\u5217 \u00b7 \u51b7\u94fe\u7269\u6d41")
                .title("\u8fde\u63a5\u4e1d\u8def \u00b7 \u901a\u8fbe\u5168\u7403")
                .description("\u4e2d\u6b27\u73ed\u5217\u6bcf\u6708\u56fa\u5b9a10\u5217\u73ed\u6b21\n\u8986\u76d620+\u56fd\u5bb6\uff0c\u81ea\u670940\u5c3a\u96c6\u88c5\u7bb1110\u4e2a")
                .ctaText("\u5168\u7403\u5e03\u5c40").ctaLink("global.html")
                .ctaText2("\u8054\u7cfb\u6211\u4eec").ctaLink2("contact.html")
                .sortOrder(2).active(true).build());

        heroBannerRepository.save(HeroBanner.builder()
                .tag("\u4e8c\u624b\u8f66\u51fa\u53e3 \u00b7 \u660e\u65af\u514b\u5c55\u5385")
                .title("\u8f66\u884c\u4e1d\u8def \u00b7 \u670d\u52a1\u5168\u7403")
                .description("\u7d2f\u8ba1\u51fa\u53e3\u4e8c\u624b\u8f66522\u53f0\n\u660e\u65af\u514b4,000\u33a1\u5c55\u5385+\u552e\u540e\u670d\u52a1\u4e2d\u5fc3")
                .ctaText("\u4e86\u89e3\u8be6\u60c5").ctaLink("products.html")
                .ctaText2("\u8054\u7cfb\u54a8\u8be2").ctaLink2("contact.html")
                .sortOrder(3).active(true).build());
    }

    private void initStatistics() {
        statisticRepository.save(Statistic.builder().label("2025\u5e74\u8fdb\u51fa\u53e3\u8d38\u6613\u989d\uff08\u5143\uff09").value("5.79").unit("\u4ebf").icon("\ud83d\udcb0").section("numbers").sortOrder(1).active(true).build());
        statisticRepository.save(Statistic.builder().label("\u540c\u6bd4\u589e\u957f\u7387").value("1,118").unit("%").icon("\ud83d\udcc8").section("numbers").sortOrder(2).active(true).build());
        statisticRepository.save(Statistic.builder().label("\u8986\u76d6\u56fd\u5bb6\u548c\u5730\u533a").value("20").unit("+").icon("\ud83c\udf0d").section("numbers").sortOrder(3).active(true).build());
        statisticRepository.save(Statistic.builder().label("\u6d77\u5916\u4ed3\u50a8\u9762\u79ef").value("8,000").unit("\u33a1").icon("\ud83c\udfe2").section("numbers").sortOrder(4).active(true).build());
    }

    private void initProducts() {
        // Export products
        productRepository.save(Product.builder().name("\u731b\u7334\u6843").category(ProductCategory.EXPORT)
                .origin("\u9655\u897f\u7709\u53bf \u00b7 \u5468\u81f3 \u00b7 \u6b66\u529f").emoji("\ud83e\udd5d")
                .description("\u9655\u897f\u731b\u7334\u6843\u662f\u516c\u53f8\u6838\u5fc3\u51fa\u53e3\u4ea7\u54c1\uff0c\u4e3b\u8981\u54c1\u79cd\u5305\u62ec\u7fe0\u9999\u3001\u5f90\u9999\u3001\u7ea2\u9633\u3001\u54d1\u7279\u7b49\u3002")
                .tags("\u7fe0\u9999,\u5f90\u9999,\u7ea2\u9633,\u54d1\u7279").badge("\u6838\u5fc3\u51fa\u53e3").sortOrder(1).active(true).build());

        productRepository.save(Product.builder().name("\u9633\u5149\u7396\u7470\u8461\u8404").category(ProductCategory.EXPORT)
                .origin("\u9655\u897f\u6e2d\u5357").emoji("\ud83c\udf47")
                .description("\u9ad8\u7aef\u9c9c\u98df\u8461\u8404\uff0c\u5546\u4e1a\u5316\u51b7\u94fe\u51fa\u53e3\u54c8\u8428\u514b\u65af\u5766\u7b49\u4e2d\u4e9a\u5e02\u573a\u3002")
                .tags("\u9c9c\u98df\u8461\u8404,\u51b7\u94fe\u8fd0\u8f93").badge("\u70ed\u9500\u4ea7\u54c1").sortOrder(2).active(true).build());

        productRepository.save(Product.builder().name("\u84dd\u8393").category(ProductCategory.EXPORT)
                .origin("\u4e91\u5357 \u00b7 \u8d35\u5dde").emoji("\ud83e\udeed")
                .description("\u9ad8\u54c1\u8d28\u84dd\u8393\uff0c\u822a\u7a7a\u51b7\u94fe\u76f4\u8fd0\uff0c48\u5c0f\u65f6\u5185\u62b5\u8fbe\u4fc4\u7f57\u65af\u3002")
                .tags("\u822a\u7a7a\u76f4\u8fd0,48\u5c0f\u65f6\u8fbe").badge("\u822a\u7a7a\u76f4\u8fd0").sortOrder(3).active(true).build());

        productRepository.save(Product.builder().name("\u82f9\u679c").category(ProductCategory.EXPORT)
                .origin("\u9655\u897f\u6d1b\u5ddd").emoji("\ud83c\udf4e")
                .description("\u6d1b\u5ddd\u82f9\u679c\uff0c\u4e3b\u8981\u54c1\u79cd\u5305\u62ec\u5bcc\u58eb\u3001\u79e6\u51a0\u3001\u52a0\u62c9\u7b49\u3002")
                .tags("\u5bcc\u58eb,\u79e6\u51a0,\u52a0\u62c9").badge("\u4f18\u8d28\u51fa\u53e3").sortOrder(4).active(true).build());

        productRepository.save(Product.builder().name("\u68a8").category(ProductCategory.EXPORT)
                .origin("\u9655\u897f\u84b2\u57ce \u00b7 \u5927\u8354").emoji("\ud83c\udf50")
                .description("\u9176\u9176\u68a8\u7b49\u4f18\u8d28\u54c1\u79cd\uff0c\u51fa\u53e3\u4e2d\u4e9a\u3001\u4fc4\u7f57\u65af\u5e02\u573a\u3002")
                .tags("\u9176\u9176\u68a8").sortOrder(5).active(true).build());

        productRepository.save(Product.builder().name("\u51ac\u67a3").category(ProductCategory.EXPORT)
                .origin("\u9655\u897f\u5927\u8354 \u00b7 \u5bcc\u5e73").emoji("\ud83c\udf52")
                .description("\u4f18\u8d28\u51ac\u67a3\uff0c\u51fa\u53e3\u4e2d\u4e9a\u3001\u4e2d\u4e1c\u5e02\u573a\u3002")
                .tags("\u51ac\u67a3").sortOrder(6).active(true).build());

        productRepository.save(Product.builder().name("\u852c\u83dc\u7c7b").category(ProductCategory.EXPORT)
                .origin("\u9655\u897f\u5404\u4ea7\u533a").emoji("\ud83e\udd66")
                .description("\u8fa3\u6912\u3001\u6d0b\u8471\u3001\u5927\u849c\u3001\u82b1\u83dc\u7b49\uff0cGAP\u8ba4\u8bc1\u54c1\u8d28\u3002")
                .tags("\u8fa3\u6912,\u6d0b\u8471,\u5927\u849c,\u82b1\u83dc").sortOrder(7).active(true).build());

        productRepository.save(Product.builder().name("\u98df\u54c1\u539f\u6599\u4e0e\u5e72\u679c").category(ProductCategory.EXPORT)
                .origin("\u591a\u4ea7\u533a").emoji("\ud83e\udd5c")
                .description("\u6838\u6843\u3001\u82b1\u751f\u3001\u9999\u6599\u3001\u5e72\u8fa3\u6912\u7b49\u3002")
                .tags("\u6838\u6843,\u82b1\u751f,\u9999\u6599").sortOrder(8).active(true).build());

        // Import products
        productRepository.save(Product.builder().name("\u5927\u9ea6 / \u997f\u7528\u5c0f\u9ea6\u7c89").category(ProductCategory.IMPORT)
                .origin("\u667a\u5229 \u00b7 \u54c8\u8428\u514b\u65af\u5766 \u00b7 \u4fc4\u7f57\u65af").emoji("\ud83c\udf3e")
                .description("\u5927\u5b97\u8fdb\u53e3\u8c37\u7269\uff0c\u4e3b\u8981\u7528\u4e8e\u997f\u6599\u3001\u98df\u54c1\u52a0\u5de5\u7b49\u3002")
                .tags("\u5927\u9ea6,\u5c0f\u9ea6\u7c89,\u997f\u6599").badge("\u5927\u5b97\u8fdb\u53e3").sortOrder(9).active(true).build());

        productRepository.save(Product.builder().name("\u725b\u6cb9\u679c").category(ProductCategory.IMPORT)
                .origin("\u667a\u5229 \u00b7 \u58a8\u897f\u54e5 \u00b7 \u79d8\u9c81").emoji("\ud83e\udd51")
                .description("\u9ad8\u54c1\u8d28\u8fdb\u53e3\u725b\u6cb9\u679c\uff0c\u6ee1\u8db3\u56fd\u5185\u9ad8\u7aef\u5e02\u573a\u9700\u6c42\u3002")
                .tags("\u9c9c\u679c\u8fdb\u53e3").badge("\u70ed\u9500\u8fdb\u53e3").sortOrder(10).active(true).build());

        productRepository.save(Product.builder().name("\u69b4\u83b2 / \u5c71\u7af9").category(ProductCategory.IMPORT)
                .origin("\u6cf0\u56fd \u00b7 \u8d8a\u5357 \u00b7 \u9a6c\u6765\u897f\u4e9a").emoji("\ud83c\udf34")
                .description("\u4e1c\u5357\u4e9a\u70ed\u5e26\u6c34\u679c\u8fdb\u53e3\uff0c\u51b7\u94fe\u8fd0\u8f93\u4fdd\u9c9c\u3002")
                .tags("\u69b4\u83b2,\u5c71\u7af9,\u51b7\u94fe").sortOrder(11).active(true).build());

        productRepository.save(Product.builder().name("\u5de7\u514b\u529b\u539f\u6599").category(ProductCategory.IMPORT)
                .origin("\u745e\u58eb \u00b7 \u6bd4\u5229\u65f6 \u00b7 \u79d1\u7279\u8fea\u74e6").emoji("\ud83c\udf6b")
                .description("\u53ef\u53ef\u8102\u3001\u53ef\u53ef\u7c89\u7b49\u5de7\u514b\u529b\u751f\u4ea7\u539f\u6599\u3002")
                .tags("\u53ef\u53ef\u8102,\u53ef\u53ef\u7c89").sortOrder(12).active(true).build());

        productRepository.save(Product.builder().name("\u6930\u67a3").category(ProductCategory.IMPORT)
                .origin("\u6c99\u7279 \u00b7 \u963f\u8054\u914b \u00b7 \u4f0a\u62c9\u514b").emoji("\ud83c\udf34")
                .description("\u4e2d\u4e1c\u4f18\u8d28\u6930\u67a3\u8fdb\u53e3\u3002")
                .tags("\u6930\u67a3").sortOrder(13).active(true).build());

        productRepository.save(Product.builder().name("\u65e0\u82b1\u679c\u5e72").category(ProductCategory.IMPORT)
                .origin("\u4f0a\u6717 \u00b7 \u571f\u8033\u5176 \u00b7 \u963f\u5bcc\u6c57").emoji("\ud83c\udf3f")
                .description("\u4f18\u8d28\u65e0\u82b1\u679c\u5e72\u8fdb\u53e3\u3002")
                .tags("\u65e0\u82b1\u679c\u5e72").sortOrder(14).active(true).build());

        productRepository.save(Product.builder().name("\u725b\u8089").category(ProductCategory.IMPORT)
                .origin("\u5df4\u897f \u00b7 \u4e4c\u514b\u5170 \u00b7 \u963f\u6839\u5ef7").emoji("\ud83e\udd69")
                .description("\u8fdb\u53e3\u8349\u997f\u725b\u8089\uff0c\u6ee1\u8db3\u56fd\u5185\u9ad8\u7aef\u9910\u996e\u5e02\u573a\u3002")
                .tags("\u8349\u997f\u725b\u8089").sortOrder(15).active(true).build());

        productRepository.save(Product.builder().name("\u5de5\u4e1a\u539f\u6599").category(ProductCategory.IMPORT)
                .origin("\u4fc4\u7f57\u65af \u00b7 \u54c8\u8428\u514b\u65af\u5766 \u00b7 \u4f0a\u6717").emoji("\u2699\ufe0f")
                .description("\u805a\u4e59\u70ef\u3001\u805a\u4e19\u70ef\u7b49\u5de5\u4e1a\u539f\u6599\u8fdb\u53e3\u3002")
                .tags("\u805a\u4e59\u70ef,\u805a\u4e19\u70ef").badge("\u5927\u5b97\u8fdb\u53e3").sortOrder(16).active(true).build());
    }

    private void initVehicleDestinations() {
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u4fc4\u7f57\u65af").flag("\ud83c\uddf7\ud83c\uddfa").description("\u6700\u5927\u51fa\u53e3\u76ee\u7684\u5730\u5e02\u573a").sortOrder(1).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u767d\u4fc4\u7f57\u65af").flag("\ud83c\udde7\ud83c\uddfe").description("\u660e\u65af\u514b4,000\u33a1\u5c55\u5385").sortOrder(2).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u54c8\u8428\u514b\u65af\u5766").flag("\ud83c\uddf0\ud83c\uddff").description("\u4e2d\u4e9a\u6838\u5fc3\u5e02\u573a").sortOrder(3).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u4e4c\u5179\u522b\u514b\u65af\u5766").flag("\ud83c\uddfa\ud83c\uddff").description("\u4e2d\u4e9a\u65b0\u5174\u5e02\u573a").sortOrder(4).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u5409\u5c14\u5409\u65af\u65af\u5766").flag("\ud83c\uddf0\ud83c\uddec").description("\u4e2d\u4e9a\u5e02\u573a").sortOrder(5).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u585e\u5c14\u7ef4\u4e9a").flag("\ud83c\uddf7\ud83c\uddf8").description("\u4e1c\u6b27\u5e02\u573a").sortOrder(6).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u963f\u585e\u62dc\u7586").flag("\ud83c\udde6\ud83c\uddff").description("\u9ad8\u52a0\u7d22\u5730\u533a").sortOrder(7).active(true).build());
        vehicleDestinationRepository.save(VehicleDestination.builder().country("\u683c\u9c81\u5409\u4e9a").flag("\ud83c\uddec\ud83c\uddea").description("\u9ad8\u52a0\u7d22\u5730\u533a").sortOrder(8).active(true).build());
    }

    private void initNewsArticles() {
        newsArticleRepository.save(NewsArticle.builder()
                .title("260\u5428\u4f18\u8d28\u731b\u7334\u6843\u7ecf\u970d\u5c14\u679c\u65af\u53e3\u5cb8\u6210\u529f\u51fa\u53e3\u4fc4\u7f57\u65af")
                .date("2025.02").category(NewsCategory.EXPORT).emoji("\ud83e\udd5d")
                .excerpt("\u516c\u53f8\u5b8c\u6210\u672c\u5e74\u5ea6\u6700\u5927\u5355\u7b14\u731b\u7334\u6843\u51fa\u53e3\u8ba2\u5355\uff0c260\u5428\u7fe0\u9999\u731b\u7334\u6843\u7ecf\u970d\u5c14\u679c\u65af\u53e3\u5cb8\u6210\u529f\u51fa\u53e3\u4fc4\u7f57\u65af\u3002")
                .content("260\u5428\u7fe0\u9999\u731b\u7334\u6843\u7ecf\u970d\u5c14\u679c\u65af\u53e3\u5cb8\u6210\u529f\u51fa\u53e3\u4fc4\u7f57\u65af\uff0c\u8fd9\u662f\u516c\u53f8\u672c\u5e74\u5ea6\u6700\u5927\u5355\u7b14\u731b\u7334\u6843\u51fa\u53e3\u8ba2\u5355\u3002")
                .featured(true).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u84dd\u8393\u822a\u7a7a\u51b7\u94fe\u76f4\u8fd4\u4fc4\u7f57\u65af\u83ab\u65af\u79d1")
                .date("2025.01").category(NewsCategory.EXPORT).emoji("\ud83e\udeed")
                .excerpt("\u4e91\u5357\u84dd\u8393\u901a\u8fc7\u822a\u7a7a\u51b7\u94fe48\u5c0f\u65f6\u5185\u62b5\u8fbe\u83ab\u65af\u79d1\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u9633\u5149\u7396\u7470\u8461\u8404\u9996\u6b21\u51fa\u53e3\u54c8\u8428\u514b\u65af\u5766")
                .date("2024.12").category(NewsCategory.EXPORT).emoji("\ud83c\udf47")
                .excerpt("14\u540a\u9633\u5149\u7396\u7470\u8461\u8404\u6210\u529f\u51fa\u53e3\u54c8\u8428\u514b\u65af\u5766\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u6d1b\u5ddd\u82f9\u679c\u6253\u5f00\u4e2d\u4e9a\u5e02\u573a\u65b0\u5c40\u9762")
                .date("2024.11").category(NewsCategory.EXPORT).emoji("\ud83c\udf4e")
                .excerpt("\u6d1b\u5ddd\u82f9\u679c\u9996\u6b21\u6279\u91cf\u51fa\u53e3\u4e2d\u4e9a\u5e02\u573a\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u667a\u5229\u725b\u6cb9\u679c\u8fdb\u53e3\u4e1a\u52a1\u6b63\u5f0f\u542f\u52a8")
                .date("2025.01").category(NewsCategory.IMPORT).emoji("\ud83e\udd51")
                .excerpt("\u516c\u53f8\u6b63\u5f0f\u542f\u52a8\u667a\u5229\u725b\u6cb9\u679c\u8fdb\u53e3\u4e1a\u52a1\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u660e\u65af\u514b4,000\u33a1\u5c55\u5385\u4e2d\u5fc3\u76db\u5927\u5f00\u4e1a")
                .date("2024.10").category(NewsCategory.VEHICLE).emoji("\ud83d\ude97")
                .excerpt("\u660e\u65af\u514b\u4e8c\u624b\u8f66\u5c55\u5385\u4e2d\u5fc3\u6b63\u5f0f\u5f00\u4e1a\uff0c\u5360\u57304,000\u33a1\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u4e8c\u624b\u8f66\u51fa\u53e3\u7a81\u7834500\u53f0\u91cc\u7a0b\u7891")
                .date("2024.09").category(NewsCategory.VEHICLE).emoji("\ud83d\ude99")
                .excerpt("\u516c\u53f8\u4e8c\u624b\u8f66\u51fa\u53e3\u4e1a\u52a1\u7d2f\u8ba1\u7a81\u7834500\u53f0\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u516c\u53f8\u83b7\u8bc4\u6768\u51cc\u793a\u8303\u533a\u91cd\u70b9\u5916\u8d38\u4f01\u4e1a")
                .date("2025.01").category(NewsCategory.COMPANY).emoji("\ud83c\udfc6")
                .excerpt("\u516c\u53f8\u83b7\u5f97\u6768\u51cc\u793a\u8303\u533a\u91cd\u70b9\u5916\u8d38\u4f01\u4e1a\u8ba4\u5b9a\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u8fea\u62dc\u6770\u8d1d\u52d2\u963f\u91cc\u4ed3\u50a8\u4e2d\u5fc3\u6295\u5165\u8fd0\u8425")
                .date("2024.11").category(NewsCategory.COMPANY).emoji("\ud83c\udfe2")
                .excerpt("\u8fea\u62dc2,000\u33a1\u591a\u6e29\u533a\u4ed3\u50a8\u4e2d\u5fc3\u6b63\u5f0f\u8fd0\u8425\u3002")
                .featured(false).active(true).build());

        newsArticleRepository.save(NewsArticle.builder()
                .title("\u53c2\u52a0\u4e0a\u6d77\u5408\u4f5c\u7ec4\u7ec7\u519c\u4e1a\u535a\u89c8\u4f1a")
                .date("2024.08").category(NewsCategory.EVENT).emoji("\ud83c\udf10")
                .excerpt("\u516c\u53f8\u53c2\u52a0\u4e0a\u6d77\u5408\u4f5c\u7ec4\u7ec7\u519c\u4e1a\u535a\u89c8\u4f1a\u3002")
                .featured(false).active(true).build());
    }

    private void initTimeline() {
        companyTimelineRepository.save(CompanyTimeline.builder().year("2022").title("\u516c\u53f8\u6b63\u5f0f\u6210\u7acb").description("\u6768\u51cc\u56fd\u5408\u8de8\u5883\u8d38\u6613\u6709\u9650\u516c\u53f8\u6b63\u5f0f\u6210\u7acb\u3002").icon("\ud83c\udfe2").sortOrder(1).build());
        companyTimelineRepository.save(CompanyTimeline.builder().year("2023").title("\u9996\u8d9f\u4e2d\u6b27\u73ed\u5217\u53d1\u8fd0").description("\u5b8c\u6210\u9996\u6b21\u4e2d\u6b27\u73ed\u5217\u8d27\u7269\u53d1\u8fd0\u3002").icon("\ud83d\ude82").sortOrder(2).build());
        companyTimelineRepository.save(CompanyTimeline.builder().year("2024").title("\u6d77\u5916\u4ed3\u4f53\u7cfb\u5efa\u7acb").description("\u83ab\u65af\u79d1\u3001\u8fea\u62dc\u6d77\u5916\u4ed3\u5e93\u5efa\u6210\u8fd0\u8425\u3002").icon("\ud83c\udf0d").sortOrder(3).build());
        companyTimelineRepository.save(CompanyTimeline.builder().year("2025.Q1").title("\u822a\u7a7a\u51b7\u94fe\u7a81\u7834").description("\u5b9e\u73b0\u822a\u7a7a\u51b7\u94fe48\u5c0f\u65f6\u5185\u62b5\u8fbe\u4fc4\u7f57\u65af\u3002").icon("\u2708\ufe0f").highlight(true).sortOrder(4).build());
        companyTimelineRepository.save(CompanyTimeline.builder().year("2025\u5168\u5e74").title("\u8d38\u6613\u989d\u589e\u957f1,118%").description("2025\u5e74\u8fdb\u51fa\u53e3\u8d38\u6613\u989d\u8fbe5.79\u4ebf\u5143\uff0c\u540c\u6bd4\u589e\u957f1,118%\u3002").icon("\ud83d\udcc8").highlight(true).sortOrder(5).build());
    }

    private void initQualifications() {
        qualificationRepository.save(Qualification.builder().title("\u4e8c\u624b\u8f66\u51fa\u53e3\u8d44\u8d28").description("\u6768\u51cc\u793a\u8303\u533a\u552f\u4e00\u5177\u5907\u4e8c\u624b\u8f66\u51fa\u53e3\u8d44\u8d28\u7684\u4f01\u4e1a").icon("\ud83d\ude97").sortOrder(1).active(true).build());
        qualificationRepository.save(Qualification.builder().title("\u4e2d\u6b27\u73ed\u5217\u5408\u4f5c").description("\u4e2d\u6b27\u73ed\u5217\u6218\u7565\u5408\u4f5c\u4f19\u4f34").icon("\ud83d\ude82").sortOrder(2).active(true).build());
        qualificationRepository.save(Qualification.builder().title("\u8fdb\u51fa\u53e3\u8bb8\u53ef").description("\u62e5\u6709\u5b8c\u5584\u7684\u8fdb\u51fa\u53e3\u8d38\u6613\u8bb8\u53ef\u8bc1").icon("\ud83d\udcdc").sortOrder(3).active(true).build());
        qualificationRepository.save(Qualification.builder().title("\u68c0\u9a8c\u68c0\u7591\u8ba4\u8bc1").description("\u519c\u4ea7\u54c1\u68c0\u9a8c\u68c0\u7591\u8ba4\u8bc1\u8d44\u8d28").icon("\u2705").sortOrder(4).active(true).build());
        qualificationRepository.save(Qualification.builder().title("\u91cd\u70b9\u5916\u8d38\u4f01\u4e1a").description("\u6768\u51cc\u793a\u8303\u533a\u91cd\u70b9\u5916\u8d38\u4f01\u4e1a\u8ba4\u5b9a").icon("\ud83c\udfc6").sortOrder(5).active(true).build());
        qualificationRepository.save(Qualification.builder().title("TIR\u8fd0\u8f93\u8d44\u8d28").description("TIR\u56fd\u9645\u516c\u8def\u8fd0\u8f93\u8bb8\u53ef\u8bc1").icon("\ud83d\ude9a").sortOrder(6).active(true).build());
        qualificationRepository.save(Qualification.builder().title("\u7efc\u4fdd\u533a\u6ce8\u518c").description("\u6768\u51cc\u7efc\u5408\u4fdd\u7a0e\u533a\u6ce8\u518c\u4f01\u4e1a").icon("\ud83c\udfe2").sortOrder(7).active(true).build());
        qualificationRepository.save(Qualification.builder().title("SCO\u5e73\u53f0\u5408\u4f5c").description("\u4e0a\u5408\u7ec4\u7ec7\u519c\u4e1a\u57fa\u5730\u5408\u4f5c\u4f01\u4e1a").icon("\ud83e\udd1d").sortOrder(8).active(true).build());
    }

    private void initLogisticsRoutes() {
        // Rail routes
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.RAIL).name("\u4fc4\u7f57\u65af\u73ed\u5217")
                .cities("\u897f\u5b89 \u2192 \u83ab\u65af\u79d1 / \u53f6\u5361\u6377\u7433\u5821").frequency("\u6bcf\u67083\u5217\u56fa\u5b9a\u73ed\u6b21").transitTime("\u7ea615\u5929")
                .description("\u7ecf\u970d\u5c14\u679c\u65af/\u963f\u62c9\u5c71\u53e3\u51fa\u5883").icon("\ud83c\uddf7\ud83c\uddfa").sortOrder(1).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.RAIL).name("\u4e2d\u4e9a\u73ed\u5217")
                .cities("\u897f\u5b89 \u2192 \u5854\u4ec0\u5e72/\u963f\u62c9\u6728\u56fe").frequency("\u6bcf\u67084\u5217\u73ed\u6b21").transitTime("\u7ea610\u5929")
                .description("\u7ecf\u970d\u5c14\u679c\u65af/\u963f\u62c9\u5c71\u53e3\u51fa\u5883").icon("\ud83c\uddf0\ud83c\uddff").sortOrder(2).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.RAIL).name("\u5df4\u5e93\u73ed\u5217")
                .cities("\u897f\u5b89 \u2192 \u5df4\u5e93").frequency("\u6bcf\u67083\u5217\u73ed\u6b21").transitTime("\u7ea612\u5929")
                .description("\u7ecf\u91cc\u6d77\u8d70\u5eca").icon("\ud83c\udde6\ud83c\uddff").sortOrder(3).active(true).build());

        // TIR routes
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.TIR).name("\u4e2d\u54c8\u7ebf\u8def")
                .cities("\u897f\u5b89 \u2192 \u963f\u62c9\u6728\u56fe").transitTime("5-7\u5929")
                .description("\u4e00\u7968\u5236\u901a\u5173\uff0c\u5168\u7a0bGPS\u8ddf\u8e2a").icon("\ud83d\ude9a").sortOrder(4).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.TIR).name("\u4e2d\u4fc4\u7ebf\u8def")
                .cities("\u897f\u5b89 \u2192 \u83ab\u65af\u79d1").transitTime("10-15\u5929")
                .description("\u516c\u8def\u76f4\u8fbe\uff0c\u7075\u6d3b\u8c03\u5ea6").icon("\ud83d\ude9a").sortOrder(5).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.TIR).name("\u8de8\u91cc\u6d77\u7ebf\u8def")
                .cities("\u897f\u5b89 \u2192 \u5df4\u5e93").transitTime("12-18\u5929")
                .description("\u7ecf\u91cc\u6d77\u8d70\u5eca\u62b5\u8fbe\u5916\u9ad8\u52a0\u7d22").icon("\ud83d\ude9a").sortOrder(6).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.TIR).name("\u4e2d\u767d\u7ebf\u8def")
                .cities("\u897f\u5b89 \u2192 \u660e\u65af\u514b").transitTime("15-22\u5929")
                .description("\u7ecf\u54c8\u8428\u514b\u65af\u5766/\u4fc4\u7f57\u65af\u62b5\u8fbe").icon("\ud83d\ude9a").sortOrder(7).active(true).build());

        // Air routes
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.AIR).name("\u897f\u5b89\u2192\u83ab\u65af\u79d1")
                .cities("\u897f\u5b89\u54b8\u9633\u56fd\u9645\u673a\u573a \u2192 \u83ab\u65af\u79d1").transitTime("<48\u5c0f\u65f6")
                .description("\u5168\u7a0b\u6e29\u63a72-8\u2103\u63a7\u5236").icon("\u2708\ufe0f").sortOrder(8).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.AIR).name("\u897f\u5b89\u2192\u963f\u62c9\u6728\u56fe")
                .cities("\u897f\u5b89 \u2192 \u963f\u62c9\u6728\u56fe").transitTime("<24\u5c0f\u65f6")
                .description("\u51b7\u94fe\u822a\u7a7a\u8fd0\u8f93").icon("\u2708\ufe0f").sortOrder(9).active(true).build());

        // Sea routes
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.SEA).name("\u6574\u7bb1 FCL")
                .description("20/40\u5c3a\u96c6\u88c5\u7bb1\u6574\u7bb1\u8fd0\u8f93").icon("\ud83d\udea2").sortOrder(10).active(true).build());
        logisticsRouteRepository.save(LogisticsRoute.builder().type(LogisticsType.SEA).name("\u62fc\u7bb1 LCL")
                .description("\u7ecf\u4e0a\u6d77\u3001\u5b81\u6ce2\u3001\u5929\u6d25\u62fc\u7bb1").icon("\ud83d\udce6").sortOrder(11).active(true).build());
    }

    private void initWarehouses() {
        warehouseRepository.save(Warehouse.builder()
                .city("\u83ab\u65af\u79d1").country("\u4fc4\u7f57\u65af").flag("\ud83c\uddf7\ud83c\uddfa").size("2,000 \u33a1")
                .description("\u83ab\u65af\u79d1\u4ed3\u50a8\u4e2d\u5fc3\uff0c\u5e38\u6e29\u51b7\u85cf\u5206\u533a\uff0c\u6570\u5b57\u5316\u7ba1\u7406")
                .features("\u5e38\u6e29\u00b7\u51b7\u85cf\u5206\u533a,\u6570\u5b57\u5316\u7ba1\u7406,\u5c0f\u5bb6\u7535\u524d\u7f6e\u4ed3,\u6700\u540e\u4e00\u516c\u91cc\u914d\u9001")
                .capabilities("\u8986\u76d6\u83ab\u65af\u79d1\u5468\u8fb9200\u516c\u91cc\u8303\u56f4").sortOrder(1).active(true).build());
        warehouseRepository.save(Warehouse.builder()
                .city("\u660e\u65af\u514b").country("\u767d\u4fc4\u7f57\u65af").flag("\ud83c\udde7\ud83c\uddfe").size("4,000 \u33a1")
                .description("\u660e\u65af\u514b\u5c55\u5385\u4e2d\u5fc3\uff0c\u4e8c\u624b\u8f66\u5c55\u5385+\u552e\u540e\u670d\u52a1")
                .features("\u4e8c\u624b\u8f66\u5c55\u5385,\u91cd\u578b\u4e3e\u5347\u5e73\u53f0,\u552e\u540e\u670d\u52a1\u4e2d\u5fc3")
                .capabilities("\u8986\u76d6\u767d\u4fc4\u7f57\u65af\u3001\u6ce2\u5170\u3001\u6ce2\u7f57\u7684\u6d77\u56fd\u5bb6").sortOrder(2).active(true).build());
        warehouseRepository.save(Warehouse.builder()
                .city("\u8fea\u62dc").country("\u963f\u8054\u914b").flag("\ud83c\udde6\ud83c\uddea").size("2,000 \u33a1")
                .description("\u8fea\u62dc\u6770\u8d1d\u52d2\u963f\u91cc\u4ed3\u50a8\uff0c\u591a\u6e29\u533a\u4ed3\u50a8")
                .features("\u5e38\u6e29/\u51b7\u85cf/\u51b7\u51bb\u591a\u6e29\u533a,5\u5428/\u33a1\u627f\u91cd\u80fd\u529b")
                .capabilities("\u8986\u76d6GCC\u516d\u56fd\u3001\u5317\u975e\u5e02\u573a").sortOrder(3).active(true).build());
    }

    private void initContactInfo() {
        contactInfoRepository.save(ContactInfo.builder()
                .type(ContactType.MAIN).city("\u6768\u51cc").country("\u4e2d\u56fd")
                .address("\u9655\u897f\u7701\u6768\u51cc\u793a\u8303\u533a\uff0c\u6768\u51cc\u7efc\u5408\u4fdd\u7a0e\u533a")
                .phone("029-XXXX-XXXX").email("info@ylguohe.com")
                .wechat("YLGH_Trade").whatsapp("+86 138 XXXX XXXX")
                .hours("\u5468\u4e00\u81f3\u5468\u4e94 9:00-18:00\uff0c\u5468\u516d 9:00-12:00")
                .sortOrder(1).active(true).build());
        contactInfoRepository.save(ContactInfo.builder()
                .type(ContactType.OVERSEAS).city("\u83ab\u65af\u79d1").country("\u4fc4\u7f57\u65af")
                .warehouseSize("2,000\u33a1\u4ed3\u50a8 \u00b7 \u51b7\u94fe\u914d\u9001")
                .sortOrder(2).active(true).build());
        contactInfoRepository.save(ContactInfo.builder()
                .type(ContactType.OVERSEAS).city("\u660e\u65af\u514b").country("\u767d\u4fc4\u7f57\u65af")
                .warehouseSize("4,000\u33a1\u5c55\u5385 \u00b7 \u4e8c\u624b\u8f66\u5c55\u5385")
                .sortOrder(3).active(true).build());
        contactInfoRepository.save(ContactInfo.builder()
                .type(ContactType.OVERSEAS).city("\u8fea\u62dc").country("\u963f\u8054\u914b")
                .warehouseSize("2,000\u33a1\u591a\u6e29\u533a\u4ed3\u50a8")
                .sortOrder(4).active(true).build());
    }
}
