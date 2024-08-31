import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {
  @Input() isVisible = false;
  @Input() modalType: string | null = null;
  @Output() close = new EventEmitter<void>();

  searchQuery: string = '';
  searchResults: { tournament: string, results: any[] }[] = [];
  expandedTournament: string | null = null;


  tournaments: string[] = [];
  selectedTournament: string = '';
  playerName: string = '';
  score: number | null = null;
  rating: number | null = null;


  tournamentData: { [key: string]: any[] } = {};
  selectedTournamentData: any[] = [];

  ngOnInit() {
    this.fetchTournaments();
    this.fetchTournamentData();
  }

  fetchTournaments() {
    this.tournaments = ['Torneio 1', 'Torneio 2', 'Torneio 3'];
  }

  fetchTournamentData() {
    this.tournamentData = {
      'Torneio 1': [
        { playerName: 'Jogador 1', score: 10, rating: 2000 },
        { playerName: 'Jogador 2', score: 20, rating: 1800 }
      ],
      'Torneio 2': [
        { playerName: 'Jogador 3', score: 15, rating: 1900 }
      ],
      'Torneio 3': [
        { playerName: 'Jogador 4', score: 25, rating: 2100 }
      ]
    };
  }

  closeModal() {
    this.isVisible = false;
    this.close.emit();
  }

  performSearch() {
    this.searchResults = this.mockSearch(this.searchQuery);
  }

  mockSearch(query: string): { tournament: string, results: any[] }[] {
    const allTournaments = Object.keys(this.tournamentData);
    return allTournaments
      .filter(tournament => tournament.toLowerCase().includes(query.toLowerCase()))
      .map(tournament => ({
        tournament,
        results: this.tournamentData[tournament]
      }));
  }

  toggleExpansion(tournament: string) {
    if (this.expandedTournament === tournament) {
      this.expandedTournament = null;
    } else {
      this.expandedTournament = tournament;
    }
  }

  registerResults() {
    console.log('Registration Details:', {
      tournament: this.selectedTournament,
      playerName: this.playerName,
      score: this.score,
      rating: this.rating
    });
  }

  onTournamentChange() {
    if (this.selectedTournament) {
      this.selectedTournamentData = this.tournamentData[this.selectedTournament] || [];
    } else {
      this.selectedTournamentData = [];
    }
  }

  exportData() {
    console.log('Exporting data for:', this.selectedTournament);
  }
}
