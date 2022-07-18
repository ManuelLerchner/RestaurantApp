import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReserveTableDialogData } from 'src/app/models/restaurant/ReserveTableDialogData';
import { TableService } from 'src/app/services/table.service';

@Component({
  selector: 'app-reserve-table',
  templateUrl: './reserve-table.component.html',
  styleUrls: ['./reserve-table.component.scss'],
})
export class ReserveTableComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<ReserveTableComponent>,
    @Inject(MAT_DIALOG_DATA) public tableData: ReserveTableDialogData,
    private tableService: TableService
  ) {}

  public errorOccured = false;

  ngOnInit(): void {}

  timeToString(time: number): string {
    const hours = Math.floor(time);
    const minutes = Math.round((time - hours) * 60);
    return (
      hours.toString().padStart(2, '0') +
      ':' +
      minutes.toString().padStart(2, '0')
    );
  }

  formatDate(date: string): string {
    date = date.replace('-', '').replace('-', '');
    const year = date.substring(0, 4);
    const month = date.substring(4, 6);
    const day = date.substring(6, 8);
    return `${day}.${month}.${year}`;
  }

  reserveTable() {
    this.errorOccured = false;
    this.tableService.reserveTable(this.tableData).subscribe({
      next: () => this.dialogRef.close(),
      error: () => {
        this.errorOccured = true;
      },
    });
  }
}
