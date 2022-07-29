package com.nhnacademy.marketgg.server.dummy;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Dummy {

    public static MemberCreateRequest getDummyMemberCreateRequest(String uuid) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        ReflectionTestUtils.setField(memberCreateRequest, "uuid", uuid);
        ReflectionTestUtils.setField(memberCreateRequest, "gender", 'M');
        LocalDate birthDate = LocalDate.of(1997, 4, 6);
        ReflectionTestUtils.setField(memberCreateRequest, "birthDate", birthDate);
        LocalDateTime now = LocalDateTime.now().withNano(0);
        ReflectionTestUtils.setField(memberCreateRequest, "ggpassUpdateAt", now);
        ReflectionTestUtils.setField(memberCreateRequest, "createdAt", now);
        ReflectionTestUtils.setField(memberCreateRequest, "updatedAt", now);

        return memberCreateRequest;
    }

    public static Member getDummyMember(Cart cart) {
        Member member = new Member(getDummyMemberCreateRequest(""), cart);

        return member;
    }

    public static Member getDummyMember(String uuid, Cart cart) {
        Member member = new Member(getDummyMemberCreateRequest(uuid), cart);
        ReflectionTestUtils.setField(member, "memberGrade", new MemberGrade(null));
        return member;
    }

    public static Member getDummyMember(String uuid, Long id, Cart cart) {
        Member member = new Member(getDummyMemberCreateRequest(uuid), cart);
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

    public static ProductCreateRequest getDummyProductCreateRequest() {
        ProductCreateRequest productRequest = new ProductCreateRequest();
        ReflectionTestUtils.setField(productRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(productRequest, "name", "자몽");
        ReflectionTestUtils.setField(productRequest, "content", "아침에 자몽 쥬스");
        ReflectionTestUtils.setField(productRequest, "totalStock", 100L);
        ReflectionTestUtils.setField(productRequest, "price", 2000L);
        ReflectionTestUtils.setField(productRequest, "description", "자몽주스 설명");
        ReflectionTestUtils.setField(productRequest, "unit", "1박스");
        ReflectionTestUtils.setField(productRequest, "deliveryType", "샛별배송");
        ReflectionTestUtils.setField(productRequest, "origin", "인도네시아");
        ReflectionTestUtils.setField(productRequest, "packageType", "냉장");
        ReflectionTestUtils.setField(productRequest, "allergyInfo", "새우알러지");

        return productRequest;
    }

    public static Asset getDummyAsset(Long id) {
        Asset asset = Asset.create();
        ReflectionTestUtils.setField(asset, "id", id);
        return asset;
    }

    public static Asset getDummyAsset() {
        return Asset.create();
    }

    public static CategorizationCreateRequest getDummyCategorizationCreateRequest() {
        CategorizationCreateRequest categorizationRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categorizationRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categorizationRequest, "name", "상품");
        ReflectionTestUtils.setField(categorizationRequest, "alias", "Products");

        return categorizationRequest;
    }

    public static Categorization getDummyCategorization() {
        return new Categorization(getDummyCategorizationCreateRequest());
    }

    public static Category getDummyCategory() {
        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);

        return new Category(categoryRequest, getDummyCategorization());
    }

    public static Product getDummyProduct(Long assetId) {
        return new Product(getDummyProductCreateRequest(), getDummyAsset(assetId), getDummyCategory());
    }

    public static Product getDummyProduct(Long productId, Long assetId) {
        Product product =
            new Product(getDummyProductCreateRequest(), getDummyAsset(assetId), getDummyCategory());
        ReflectionTestUtils.setField(product, "id", productId);

        return product;
    }

    public static ProductToCartRequest getDummyProductToCartRequest(Long productId) {
        ProductToCartRequest request = new ProductToCartRequest();
        ReflectionTestUtils.setField(request, "id", productId);
        ReflectionTestUtils.setField(request, "amount", 3);

        return request;
    }

    public static ProductToCartRequest getDummyProductToCartRequest(Long productId, Integer amount) {
        ProductToCartRequest request = new ProductToCartRequest();
        ReflectionTestUtils.setField(request, "id", productId);
        ReflectionTestUtils.setField(request, "amount", amount);

        return request;
    }

    public static MemberInfo getDummyMemberInfo(Long id, Cart cart) {
        LocalDate birthDate = LocalDate.of(1997, 4, 6);
        LocalDateTime ggpassUpdatedAt = LocalDateTime.now();
        return new MemberInfo(id, cart, null, 'M', birthDate, ggpassUpdatedAt);
    }

    public static Cart getDummyCart(Long id) {
        Cart cart = new Cart();
        ReflectionTestUtils.setField(cart, "id", id);
        return cart;
    }

    public static CartProduct getCartProduct(Long cartId, Long productId, Integer amount) {
        return new CartProduct(getDummyCart(cartId), getDummyProduct(productId), amount);
    }
}
