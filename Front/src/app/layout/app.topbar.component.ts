import {Component, ElementRef, HostListener, ViewChild} from '@angular/core';
import {MenuItem, OverlayService} from 'primeng/api';
import { LayoutService } from "./service/app.layout.service";
import { OverlayPanel } from 'primeng/overlaypanel';
@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent {
  dropdownVisible: boolean = false;

    items!: MenuItem[];

    @ViewChild('menubutton') menuButton!: ElementRef;

    @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

    @ViewChild('topbarmenu') menu!: ElementRef;

  @ViewChild('overlayPanel') overlayPanel: OverlayPanel | undefined;

    constructor(public layoutService: LayoutService) { }
  toggleDropdown(event: Event) {
    this.dropdownVisible = !this.dropdownVisible;
    event.stopPropagation(); // Prevent event from bubbling up to document click listener
  }

  onDropdownItemClick(event: Event) {
    // Implement click logic for dropdown items here
    this.dropdownVisible = false; // Hide dropdown after item is clicked
    event.stopPropagation(); // Prevent event from bubbling up to document click listener
  }

  // Close dropdown when clicking outside of it
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event) {
    const target = event.target as HTMLElement; // Explicitly cast event.target to HTMLElement
    if (!target.closest('.topbar')) {
      this.dropdownVisible = false;
    }
  }

}
