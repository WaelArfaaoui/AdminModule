import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent implements OnInit {

  users = [
    {
      id: 1,
      name: 'Daniel Kristeen',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'daniel@website.com',
      phone: '999 - 444 - 555',
      addedDate: '15 Mar 1988',
      addedTime: '10:55 AM',
      category: 'Admin'
    },
    {
      id: 2,
      name: 'Emma Smith',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'emma@website.com',
      phone: '999 - 555 - 666',
      addedDate: '15 Mar 1855',
      addedTime: '10:00 AM',
      category: 'Admin'
    },
    {
      id: 3,
      name: 'Olivia Johnson',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'olivia@website.com',
      phone: '999 - 777 - 888',
      addedDate: '17 Aug 1988',
      addedTime: '12:55 AM',
      category: 'User'
    },
    {
      id: 4,
      name: 'Isabella Williams',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'isabella@website.com',
      phone: '999 - 999 - 000',
      addedDate: '26 Mar 1999',
      addedTime: '10:55 AM',
      category: 'Subscriber'
    },
    {
      id: 5,
      name: 'Sophia Jones',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'sophia@website.com',
      phone: '999 - 111 - 222',
      addedDate: '16 Aug 2001',
      addedTime: '10:55 AM',
      category: 'Admin'
    },
    {
      id: 6,
      name: 'Charlotte Brown',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'charlotte@website.com',
      phone: '999 - 333 - 444',
      addedDate: '15 Mar 1988',
      addedTime: '10:55 AM',
      category: 'Admin'
    },
    {
      id: 7,
      name: 'Sophia Jones',
      location: 'Texas, United States',
      occupation: 'Visual Designer',
      pastOccupation: 'Past: Teacher',
      email: 'sophia@website.com',
      phone: '999 - 111 - 222',
      addedDate: '16 Aug 2001',
      addedTime: '10:55 AM',
      category: 'Admin'
    },
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
