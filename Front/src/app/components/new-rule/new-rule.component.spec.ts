import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FormArray, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { By } from '@angular/platform-browser';
import {Observable, of, throwError} from 'rxjs';

import { NewRuleComponent } from './new-rule.component';
import { FormBuilder } from '@angular/forms';
import { MessageService } from 'primeng/api';
import {AttributeService, RuleService, CategoryService, CategoryDto} from '../../../open-api';
import { Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {DialogService} from "primeng/dynamicdialog";
import {HttpClient, HttpErrorResponse, HttpEventType, HttpHandler, HttpResponse} from "@angular/common/http";


class MockAttributeService {
    getAllAttributes() {
        return of([]);
    }
}

class MockRuleService {
    saveRule() {
        return of({});
    }
}

class MockCategoryService {
    getAllCategories() {
        return of([]);
    }
}

class MockRouter {
    navigate() {}
}


describe('NewRuleComponent', () => {
    let fixture: ComponentFixture<NewRuleComponent>;
    let component: NewRuleComponent;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [ FormsModule, ReactiveFormsModule, HttpClientTestingModule ],
            declarations: [
                NewRuleComponent
            ],
            providers: [
                FormBuilder,
                MessageService , DialogService , HttpClient , HttpHandler ,
                { provide: AttributeService, useClass: MockAttributeService },
                { provide: RuleService, useClass: MockRuleService },
                { provide: CategoryService, useClass: MockCategoryService },
                { provide: Router, useClass: MockRouter }
            ]
        }).compileComponents();
        fixture = TestBed.createComponent(NewRuleComponent);
        component = fixture.componentInstance;
        component.ngOnInit();
    });

    afterEach(() => {
        fixture.destroy();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call ngOnInit and initialize form and load categories and attributes', () => {
        spyOn(component, 'initForm');
        spyOn(component, 'loadCategories');
        spyOn(component, 'loadAttributes');
        component.ngOnInit();
        expect(component.initForm).toHaveBeenCalled();
        expect(component.loadCategories).toHaveBeenCalled();
        expect(component.loadAttributes).toHaveBeenCalled();
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

    it('should load attributes successfully', () => {
        const dummyAttributes = [{ name: 'Attribute 1' }, { name: 'Attribute 2' }];
        const httpResponse = new HttpResponse({ body: dummyAttributes });
        spyOn(component['attributeService'], 'getAllAttributes').and.returnValue(of(httpResponse));

        component.loadAttributes();

    });

    it('should handle error when loading attributes', () => {
        const error = new Error('Failed to load attributes');
        spyOn(component['attributeService'], 'getAllAttributes').and.returnValue(throwError(error));
        spyOn(console, 'error');

        component.loadAttributes();

        expect(console.error).toHaveBeenCalledWith('Error loading attributes:', error);
    });

    describe('addAttribute()', () => {
        it('should add a new attribute group to the form array', () => {
            // Arrange
            const attributeArray = component.ruleForm?.get('attributeDtos') as FormArray;
            const initialLength = attributeArray?.length ?? 0;

            // Act
            component.addAttribute();

            // Assert
            expect(attributeArray?.length).toBe(initialLength + 1);
        });
    });

    describe('removeAttribute()', () => {
        it('should remove the attribute group at the specified index from the form array', () => {
            // Arrange
            const attributeArray = component.ruleForm?.get('attributeDtos') as FormArray;
            const initialLength = attributeArray?.length ?? 0;
            const indexToRemove = initialLength - 1;

            // Act
            component.removeAttribute(indexToRemove);

            // Assert
            expect(attributeArray?.length).toBe(initialLength - 1);
        });

        it('should not remove anything if index is out of bounds', () => {
            // Arrange
            const attributeArray = component.ruleForm?.get('attributeDtos') as FormArray;
            const initialLength = attributeArray?.length ?? 0;
            const indexToRemove = initialLength + 1;

            // Act
            component.removeAttribute(indexToRemove);

            // Assert
            expect(attributeArray?.length).toBe(initialLength);
        });
    });


});
