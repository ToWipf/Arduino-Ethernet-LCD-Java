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
  }

}
