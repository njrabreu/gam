import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
	providedIn: 'root'
})
export class GameService {

	constructor(private http: HttpClient) { }

	getAllGames() {
		return this.http.get('server/game/all');
	}

	getPlatforms() {
		return this.http.get('server/game/platforms');
	}

	getRates() {
		return this.http.get('server/game/rates');
	}

	getStatuses() {
		return this.http.get('server/game/statuses');
	}

	getGame(id: number) {
		return this.http.get('server/game/' + id);
	}

	createGameRegistration(game: any) {
		let body = game;
		return this.http.post('server/game/new', body);
	}

	updateGameRegistration(game: any) {
		let body = game;
		return this.http.post('server/game/update', body);
	}
}
