import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'Wipf';
  public _opened: boolean = false;

  public _toggleSidebar() {
    this._opened = !this._opened;
  }
}
