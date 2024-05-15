import {ComponentFixture, TestBed} from '@angular/core/testing';
import {DonutComponent} from './donut.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {CategoryDto, CategoryService} from "../../../open-api";
import {of, throwError} from 'rxjs';
import {HttpErrorResponse, HttpEvent, HttpResponse} from '@angular/common/http';

describe('DonutComponent', () => {
    let component: DonutComponent;
    let fixture: ComponentFixture<DonutComponent>;
    let categoryService: CategoryService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DonutComponent],
            imports: [HttpClientTestingModule],
            providers: [CategoryService],
        })
            .compileComponents();

        fixture = TestBed.createComponent(DonutComponent);
        component = fixture.componentInstance;
        categoryService = TestBed.inject(CategoryService);
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load categories successfully', () => {
        const dummyCategories = [{ name: 'Category 1' }, { name: 'Category 2' }];
        const httpResponse = new HttpResponse({ body: dummyCategories });
        spyOn(component['categoryService'], 'getAllCategories').and.returnValue(of(httpResponse));
        component.loadCategories();
    });
    it('should handle error when loading categories', () => {
        const error = new HttpErrorResponse({ status: 404, statusText: 'Not Found' });
        spyOn(component['categoryService'], 'getAllCategories').and.returnValue(throwError(error));
        spyOn(console, 'error');
        component.loadCategories();
    });

});
