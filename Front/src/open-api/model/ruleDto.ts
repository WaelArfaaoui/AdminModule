/**
 * OpenApi specification 
 * OpenApi documentation for Admin Module Demo
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { CategoryDto } from './categoryDto';
import { AttributeDataDto } from './attributeDataDto';


export interface RuleDto { 
    id?: number;
    name?: string;
    description?: string;
    category?: CategoryDto;
    enabled?: boolean;
    createDate?: string;
    lastModified?: string;
    createdBy?: number;
    lastModifiedBy?: number;
    attributeDtos?: Array<AttributeDataDto>;
}

