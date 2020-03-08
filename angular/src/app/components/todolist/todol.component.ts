import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-todoli',
  templateUrl: './todol.component.html',
  styleUrls: ['./todol.component.less']
})
export class ToDoLiComponent implements OnInit {

  constructor(
    private http: HttpClient,
  ) { }

  ngOnInit() {
    this.getAll();
  }
  
  private getAll(): void {
    this.http.get("http://192.168.2.10:8080/todolist/getAllJson")
      .subscribe((resdata) => {
        const list = JSON.parse(resdata.toString());
        console.log(list);
        //TODO:
        list.forEach((item) => {
          console.log(item);
        });
      });
  }
}
