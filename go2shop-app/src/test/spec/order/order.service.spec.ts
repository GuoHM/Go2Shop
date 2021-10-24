import { HttpTestingController } from '@angular/common/http/testing';
import { TestBed, getTestBed, async } from "@angular/core/testing";
import { Go2ShopTestModule } from '../test.module';
import { OrderService } from 'app/pages/order/order.service';
import { Order, OrderSearchDTO } from 'app/pages/order/order.model';

describe('OrderService Tests', () => {
    let injector: TestBed;
    let service: OrderService;
    let httpMock: HttpTestingController;
    let cartApi: string;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [Go2ShopTestModule]
        });
        injector = getTestBed();
        service = injector.get(OrderService);
        httpMock = injector.get(HttpTestingController);
        cartApi = 'api/orderService/order';
    })
    
    it('getOrder test', async(() => {
        // GIVEN
        const mockOrderId = 1;
        // WHEN
        service.getOrder(mockOrderId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/order/${mockOrderId}`);
    }));

    it('placeOrder test', async(() => {
        // GIVEN
        const mockOrderDTO = new Order();
        // WHEN
        service.placeOrder(mockOrderDTO).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/createOrder`);
    }));

    it('orderReceived test', async(() => {
        // GIVEN
        const mockOrderDTO = new Order();
        // WHEN
        service.orderReceived(mockOrderDTO).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/orderReceived`);
    }));

    it('confirmDelivery test', async(() => {
        // GIVEN
        const mockOrderDTO = new Order();
        // WHEN
        service.confirmDelivery(mockOrderDTO).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/confirmDelivery`);
    }));

    it('cancelPayment test', async(() => {
        // GIVEN
        const mockOrderIds = [1];
        // WHEN
        service.cancelPayment(mockOrderIds).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/cancelPayment`);
    }));

    it('searchOrders test', async(() => {
        // GIVEN
        const mockSearchDTO = new OrderSearchDTO();
        // WHEN
        service.searchOrders(mockSearchDTO).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/order/search`);
    }));

    it('confirmPayment test', async(() => {
        // GIVEN
        const mockOrderIds = [1];
        const mockPayOption = 'Test'
        // WHEN
        service.confirmPayment(mockPayOption, mockOrderIds).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/confirmPayment/${mockPayOption}`);
    }));

    afterEach(() => {
        httpMock.verify();
    })
})