import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tool-view',
  templateUrl: './tool-view.component.html',
  styleUrls: ['./tool-view.component.scss']
})
export class ToolViewComponent implements OnInit {
  isModalVisible = false;
  modalType: string | null = null;

  openModal(type: string) {
    this.modalType = type;
    this.isModalVisible = true;
  }

  handleModalClose() {
    this.isModalVisible = false;
    this.modalType = null;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
