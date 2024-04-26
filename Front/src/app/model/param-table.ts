export class ParamTable {
  tableName: string = "";
  columns: string[] = [];
  primaryKey: string = ""; // Assign a type to primaryKey
  selectedColumns: string[] = [];
  sortByColumn: string = "";
  sortOrder: string = "";
  totalRows:number=0
  limit: number = 0;
  data: any[] = [];
  newRow: any = {}; // Assign an appropriate type to newRow
  showNewRow: boolean = false;
  editedValue: { [key: string]: { [key: string]: any } } = {}; // Nested dictionary for edited values
}
