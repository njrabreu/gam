import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { GameService } from '../../services/game.service';
import { LoginService } from '../../services/login.service';

@Component({
	selector: 'app-game',
	templateUrl: './game.component.html',
	styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

	platforms: any = [];
	rates: any = [];
	statuses: any = [];
	gameform: FormGroup =
		new FormGroup({
			Id: new FormControl(''),
			TypeForm: new FormControl('true'),
			Title: new FormControl('', Validators.required),
			Completion: new FormControl(''),
			Platform: new FormControl('', Validators.required),
			Rate: new FormControl('', Validators.required),
			Status: new FormControl('', Validators.required)
		});;

	validMessage: string = "";
	currentUser: string = "";

	constructor(private gameService: GameService, private loginService: LoginService, private route: ActivatedRoute) { }

	ngOnInit(): void {
		this.currentUser = localStorage.getItem('id') as string;
		this.loginService.validateUserInSession(this.currentUser);

		// Fill in drop down lists with database values
		this.gameService.getPlatforms().subscribe(
			data => { this.platforms = data },
			err => console.error(err),
			() => console.log('All Platforms Loaded!')
		);

		this.gameService.getRates().subscribe(
			data => { this.rates = data },
			err => console.error(err),
			() => console.log('All Rates Loaded!')
		);

		this.gameService.getStatuses().subscribe(
			data => { this.statuses = data },
			err => console.error(err),
			() => console.log('All Statuses Loaded!')
		);

		// Load selected game information (if not new record)
		if (this.route.snapshot.params.id != 0) {
			this.getGameID(this.route.snapshot.params.id);
		}
	}

	getGameID(id: number) {

		this.gameService.getGame(id).subscribe(
			data => {
				console.log(data);
				// Refresh form fields with game selected
				this.gameform.setValue({
					Id: (data as any).id,
					TypeForm: false,
					Title: (data as any).title,
					Completion: (data as any).completion, // Current day
					Platform: this.platforms[(data as any).platform - 1],
					Rate: this.rates[(data as any).rate - 1],
					Status: this.statuses[(data as any).status - 1]
				});
			},
			err => {
				this.validMessage = "Error when fetching game information. Try later!";

				console.error(err);

				if (err.status == 403) {
					this.loginService.showErrorPage();
				}
			}
		);
	}

	resetFormOnSuccess() {
		this.gameform.controls['Id'].reset();
		this.gameform.controls['Title'].reset();
		this.gameform.controls['Completion'].reset();
		this.gameform.controls['Platform'].reset();
		this.gameform.controls['Rate'].reset();
		this.gameform.controls['Status'].reset();
	}

	registerNewGame() {

		let payload: string;
		let auxStr: string;
		let typeRequest: boolean;
		let id: number;
		let title: string;
		let completion: Date;
		let platform: number;
		let rate: number;
		let status: number;

		// Parse elements one by one
		typeRequest = this.gameform.controls.TypeForm.value;
		id = this.gameform.controls.Id.value;
		title = this.gameform.controls.Title.value;
		completion = this.gameform.controls.Completion.value;

		auxStr = this.gameform.controls.Platform.value;
		platform = parseInt(auxStr.substring(0, auxStr.indexOf('-')));

		auxStr = this.gameform.controls.Rate.value;
		rate = parseInt(auxStr.substring(0, auxStr.indexOf('-')));

		auxStr = this.gameform.controls.Status.value;
		status = parseInt(auxStr.substring(0, auxStr.indexOf('-')));

		console.log('IsNew?: ' + typeRequest);

		// typeRequest = true => New Game
		if (typeRequest) {
			// Build payload for new game (no ID needed)
			payload = '{"title":"' + title + '","completion":"' + completion + '","platform":"' + platform + '","rate":"' + rate + '","status":"' + status + '"}';

			this.gameService.createGameRegistration(payload).subscribe(
				data => {
					this.validMessage = "New Game saved successfully!";
					this.resetFormOnSuccess();
				},
				err => {
					this.validMessage = "Error when saving game. Try later!";

					console.error(err);

					if (err.status == 403) {
						this.loginService.showErrorPage();
					}
				}
			);

			console.log('Form clicked: ' + payload);
		}
		else {
			// Build payload for existing game (ID needed)
			payload = '{"id":"' + id + '","title":"' + title + '","completion":"' + completion + '","platform":"' + platform + '","rate":"' + rate + '","status":"' + status + '"}';

			this.gameService.updateGameRegistration(payload).subscribe(
				data => {
					this.validMessage = "Game updated successfully!";
					this.resetFormOnSuccess();
				},
				err => {
					this.validMessage = "Error when updating game. Try later!";

					console.error(err);

					if (err.status == 403) {
						this.loginService.showErrorPage();
					}
				}
			);

			console.log('Form clicked: ' + payload);
		}
	}

	logout() {
		this.loginService.logout();
	}
}
