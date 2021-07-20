import { Component, OnInit } from '@angular/core';
import { GameService } from '../../services/game.service';
import { LoginService } from '../../services/login.service';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

	public allgames: any;
	currentUser: string = "";

	constructor(private gameService: GameService, private loginService: LoginService) { }

	ngOnInit(): void {
		this.getAllGames();
		this.currentUser = localStorage.getItem('id') as string;
		this.loginService.validateUserInSession(this.currentUser);
	}

	getAllGames() {
		this.gameService.getAllGames().subscribe(
			data => { this.allgames = data },
			err => {
				console.error(err);

				if (err.status == 403) {
					this.loginService.showErrorPage();
				}
			},
			() => console.log('All Games Loaded!')
		);
	}

	logout() {
		this.loginService.logout();
	}
}
