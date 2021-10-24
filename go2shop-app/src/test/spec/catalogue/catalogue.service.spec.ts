import { HttpTestingController } from '@angular/common/http/testing';
import { TestBed, getTestBed, async } from "@angular/core/testing";
import { Go2ShopTestModule } from '../test.module';
import { OrderService } from 'app/pages/order/order.service';
import { Order, OrderSearchDTO } from 'app/pages/order/order.model';
import { CatalogueService } from 'app/pages/catalogue/catalogue.service';
import { ProductReview, ProductSearch } from 'app/pages/catalogue/product.model';

describe('CatalogueService Tests', () => {
    let injector: TestBed;
    let service: CatalogueService;
    let httpMock: HttpTestingController;
    let cartApi: string;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [Go2ShopTestModule]
        });
        injector = getTestBed();
        service = injector.get(CatalogueService);
        httpMock = injector.get(HttpTestingController);
        cartApi = 'api/catalogueService/catalogue';
    })
    
    it('getCatalogue test', async(() => {
        // WHEN
        service.getCatalogue().subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/catalogue`);
    }));

    it('getProduct test', async(() => {
        // GIVEN
        const mockProdId = 1;
        // WHEN
        service.getProduct(mockProdId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/product/${mockProdId}`);
    }));

    it('getProductRatings test', async(() => {
        // GIVEN
        const mockProdId = 1;
        // WHEN
        service.getProductRatings(mockProdId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/product/ratings/${mockProdId}`);
    }));

    it('getProductReviews test', async(() => {
        // GIVEN
        const mockProdId = 1;
        // WHEN
        service.getProductReviews(mockProdId).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/product/reviews/${mockProdId}`);
    }));

    it('getRecommendedProducts test', async(() => {
        // WHEN
        service.getRecommendedProducts().subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'GET' });
        expect(request.request.url).toEqual(cartApi + `/product/getRecommendedProducts`);
    }));

    it('addProductReview test', async(() => {
        // GIVEN
        const mockReview = new ProductReview();
        // WHEN
        service.addProductReview(mockReview).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/addProductReview`);
    }));

    it('search test', async(() => {
        // GIVEN
        const mockSearch = new ProductSearch();
        // WHEN
        service.search(mockSearch).subscribe();
        // THEN
        const request = httpMock.expectOne({ method: 'POST' });
        expect(request.request.url).toEqual(cartApi + `/catalogue/search`);
    }));

    afterEach(() => {
        httpMock.verify();
    })
})