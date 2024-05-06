import {ColumnInfo} from "./column-info";

export class TableInfo {
  name: string="";
  type: string |null = null;
  pk: ColumnInfo ={name:"",type:""};
  totalRows: number=0;
  columns: ColumnInfo[] =[{name:"",type:""}];
  selectedColumns: string[];
  sortByColumn: string="";
  sortOrder: string="";
  limit: number=0;
  data: any[]=[];
  showNewRow: boolean=false;
  editedValue: { [rowId: string]: { [column: string]: any } }={};
  isExpanded: boolean=false; // Property for expansion state
  currentPage: number=1; // Property for current page
  newRow={}
  newRows: any[]=[];
  totalPageCount=0;
  offset=0;
  search:string="";
  deleteRequests: any[]=[];
  updateRequests: any[]=[];
  constructor() {
    this.selectedColumns = [];

  }
}
