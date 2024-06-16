package br.com.solidtechsolutions.apipagamentos.services.impls;

import br.com.solidtechsolutions.apipagamentos.models.Produto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Value("${mercadopago.token}")
    private String authToken;

    private String contentType = "application/json";

    @Override
    public ResponseEntity<String> executePayment(Produto produto) {
        try {

            MercadoPagoConfig.setAccessToken(authToken);
            PreferenceClient client = new PreferenceClient();
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(String.valueOf(produto.getId()))
                            .title(produto.getName())
                            .description(produto.getDescription())
                            //.pictureUrl("http://www.myapp.com/myimage.jpg")
                            //.categoryId("car_electronics")
                            .quantity(1)
                            .currencyId("BRL")
                            .unitPrice(parsePrice(produto.getPrice()))
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
                                    .success("https://solidtechsolutions.com.br/success")
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
            return ResponseEntity.badRequest().body("erri ao gerar pagamento");
        }
    }




    private BigDecimal parsePrice(String priceStr) {
        // Attempt to parse the price using the US locale (dollar format)
        Double valor = Double.valueOf(priceStr.replaceAll(",", "."));
        return BigDecimal.valueOf(valor);
    }
}
