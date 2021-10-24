import { HttpTestingController } from '@angular/common/http/testing';
import { TestBed, getTestBed, async } from "@angular/core/testing";
import { ShoppingCartService } from 'app/pages/cart/shopping-cart.service';
import { Go2ShopTestModule } from '../test.module';
import { ShoppingCart } from 'app/pages/cart/shopping-cart.model';
import { ShoppingCartProduct } from 'app/pages/cart/shopping-cart-product.model';

describe('ShoppingCartService Tests', () => {
    let injector: TestBed;
    let service: ShoppingCartService;
    let httpMock: HttpTestingController;
    let cartApi: string;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [Go2ShopTestModule]
        });
        injector = getTestBed();
        service = injector.get(ShoppingCartService);
        httpMock = injector.get(HttpTestingController);
        cartApi = 'api/cartService/shoppingCart';
    })
    
    it('getShoppingCart test', async(() => {
        // GIVEN
        const mockUserId = 1;
        // WHEN
        service.getShoppingCart(mockUserId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCart/${mockUserId}`);
    }));
    
    it('getCartSize test', async(() => {
        // GIVEN
        const mockUserId = 1;
        // WHEN
        service.getCartSize(mockUserId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/cart/size/${mockUserId}`);
    }));

    it('getShoppingCartProduct test', async(() => {
        // GIVEN
        const mockCartProductId = 1;
        // WHEN
        service.getShoppingCartProduct(mockCartProductId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCartProduct/product/${mockCartProductId}`);
    }));

    it('getAllShoppingCartProduct test', async(() => {
        // GIVEN
        const mockCartId = 1;
        // WHEN
        service.getAllShoppingCartProduct(mockCartId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCartProduct/shoppingCart/${mockCartId}`);
    }));

    it('createShoppingCart test', async(() => {
        // GIVEN
        const mockCartDTO = new ShoppingCart();
        // WHEN
        service.createShoppingCart(mockCartDTO).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCart/create`);
    }));

    it('createShoppingCartProduct test', async(() => {
        // GIVEN
        const mockCartProductDTO = new ShoppingCartProduct();
        // WHEN
        service.createShoppingCartProduct(mockCartProductDTO).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCartProduct/create`);
    }));

    it('deleteAllProduct test', async(() => {
        // GIVEN
        const mockCartId = 1;
        // WHEN
        service.deleteAllProduct(mockCartId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCartProduct/delete`);
    }));

    it('deleteShoppingCartProduct test', async(() => {
        // GIVEN
        const mockCartProductId = 1;
        // WHEN
        service.deleteShoppingCartProduct(mockCartProductId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCartProduct/delete/${mockCartProductId}`);
    }));

    it('updateQuantity test', async(() => {
        // GIVEN
        const mockProductId = 1;
        const mockQty = 1;
        const mockCartId = 1;
        // WHEN
        service.updateQuantity(mockProductId, mockQty, mockCartId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'PUT' });
        expect(request.request.url).toEqual(cartApi + `/shoppingCartProduct/update/${mockProductId}/${mockCartId}/${mockQty}`);
    }));

    afterEach(() => {
        httpMock.verify();
    })
})