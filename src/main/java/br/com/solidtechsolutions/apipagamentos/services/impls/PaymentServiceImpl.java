package br.com.solidtechsolutions.apipagamentos.services.impls;

import br.com.solidtechsolutions.apipagamentos.models.Product;
import br.com.solidtechsolutions.apipagamentos.models.Produto;
import br.com.solidtechsolutions.apipagamentos.models.Review;
import br.com.solidtechsolutions.apipagamentos.services.PaymentService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ProductService productService;

    @Value("${mercadopago.token}")
    private String authToken;

    private String contentType = "application/json";

    @Override
    public ResponseEntity<String> executePayment(Produto produto) {
        try {

            boolean patialyPayment = produto.getPrice() == null || produto.getPrice().isEmpty() || produto.getPrice().isBlank();

            MercadoPagoConfig.setAccessToken(authToken);
            PreferenceClient client = new PreferenceClient();
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(String.valueOf(produto.getId()))
                            .title(produto.getName())
                            .description(produto.getDescription()+"\n Mensagem de Felicitações: "+produto.getGiftMessage())
                            //.pictureUrl("http://www.myapp.com/myimage.jpg")
                            //.categoryId(produto.getName())
                            .quantity(1)
                            .currencyId("BRL")
                            .unitPrice(parsePrice(produto))
                            .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);
            PreferenceFreeMethodRequest freeMethod =
                    PreferenceFreeMethodRequest.builder()
                            .id(1L).build();
            List<PreferenceFreeMethodRequest> freeMethodList = new ArrayList<>();
            freeMethodList.add(freeMethod);
            List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
            excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

            List<PreferencePaymentMethodRequest> excludedPaymentMethods = new ArrayList<>();
            excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("").build());

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .backUrls(
                            PreferenceBackUrlsRequest.builder()
                                    .success(getSuccessUrl(patialyPayment, produto))
                                    .failure("https://solidtechsolutions.com.br/failure")
                                    .pending("https://solidtechsolutions.com.br/pending")
                                    .build())
                    .differentialPricing(
                            PreferenceDifferentialPricingRequest.builder()
                                    .id(1L)
                                    .build())
                    .expires(false)
                    .items(items)
                    .marketplaceFee(new BigDecimal("0"))
                    .payer(
                            PreferencePayerRequest.builder()
                                    //.name("Test")
                                    //.surname("User")
                                    //.email("your_test_email@example.com")
                                    //.phone(PhoneRequest.builder().areaCode("11").number("4444-4444").build())
                                    //.identification(
                                    //        IdentificationRequest.builder().type("CPF").number("19119119100").build())
                                    //.address(
                                    //        AddressRequest.builder()
                                    //                .zipCode("06233200")
                                    //                .streetName("Street")
                                    //                .streetNumber("123")
                                    //                .build())
                                    .build())
                    //.additionalInfo("Discount: 12.00")
                    .autoReturn("all")
                    .binaryMode(true)
                    //.externalReference("1643827245")
                    .marketplace("marketplace")
                    //.notificationUrl("http://notificationurl.com")
                    .operationType("regular_payment")
                    //.paymentMethods(
                    //        PreferencePaymentMethodsRequest.builder()
                    //                .defaultPaymentMethodId("master")
                    //                .excludedPaymentTypes(excludedPaymentTypes)
                    //                .excludedPaymentMethods(excludedPaymentMethods)
                    //                .installments(5)
                    //                .defaultInstallments(1)
                    //                .build())
                    //.shipments(
                    //        PreferenceShipmentsRequest.builder()
                    //                .mode("custom")
                    //                .localPickup(false)
                    //                .defaultShippingMethod(null)
                    //                .freeMethods(freeMethodList)
                    //                .cost(BigDecimal.TEN)
                    //                .freeShipping(false)
                    //                .dimensions("10x10x20,500")
                    //                .receiverAddress(
                    //                        PreferenceReceiverAddressRequest.builder()
                    //                                .zipCode("06000000")
                    //                                .streetNumber("123")
                    //                                .streetName("Street")
                    //                                .floor("12")
                    //                                .apartment("120A")
                    //                                .build())
                    //                .build())
                    .statementDescriptor("Test Store")
                    .build();

            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.ok(preference.getInitPoint());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("erro ao gerar pagamento");
        }
    }

    private String getSuccessUrl(boolean patialyPayment, Produto produto) {
        Product product = productService.getProductById(Long.valueOf(produto.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + produto.getId()));

        // Busca o ID do último review baseado no maior ID
        Long lastReviewId = product.getReviews().stream()
                .max(Comparator.comparingLong(Review::getId)) // Busca o review com o maior ID
                .map(Review::getId) // Mapeia para o ID do review
                .orElse(null); // Caso não encontre um review, retorna null


        // Retorna a URL de sucesso com o ID do último review
        if (patialyPayment) {
            return "https://solidtechsolutions.com.br/success/" + produto.getId() + "/quotas/" + produto.getQuotaQuantity()
                    + "/review/" + lastReviewId;
        } else {
            return "https://solidtechsolutions.com.br/success/" + produto.getId()
                    + "/review/" + lastReviewId;
        }
    }



    private BigDecimal parsePrice(Produto produto) {
        // Attempt to parse the price using the US locale (dollar format)
        String priceStr;
        if (produto.getPrice() != null){
            priceStr = produto.getPrice();
        }else{
            priceStr = produto.getTotalPrice();
        }
        Double valor = Double.valueOf(priceStr.replaceAll(",", "."));
        return BigDecimal.valueOf(valor);
    }
}
