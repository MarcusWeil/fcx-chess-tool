import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FcxService } from './fcxService.service';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {

  constructor(private fcxService: FcxService) {}

  @Input() isVisible = false;
  @Input() modalType: string | null = null;
  @Output() close = new EventEmitter<void>();

  searchQuery: string = '';
  searchResults: { tournament: string, results: any[] }[] = [];
  expandedTournament: string | null = null;

  tournaments: any[] = [];
  selectedTournament: string = '';
  playerName: string = '';
  score: number | null = null;
  rating: number | null = null;

  tournamentData: { [key: string]: any[] } = {};
  selectedTournamentData: any[] = [];
  loading: boolean = false;
  errorMessage: string = '';

  ngOnInit() {
    this.fetchTournaments();
    this.fetchTournamentData();
  }

  fetchTournaments() {
    this.loading = true;
    this.fcxService.fetchTournaments()
      .pipe(
        catchError(error => {
          this.errorMessage = 'Error fetching tournaments. Please try again later.';
          this.loading = false;
          return of([]);
        })
      )
      .subscribe(tournaments => {
        this.tournaments = tournaments;
        this.loading = false;
      });
  }

  fetchTournamentData() {
    this.fcxService.fetchTournaments().subscribe(tournaments => {
      this.tournamentData = tournaments.reduce((acc: { [x: string]: any; }, tournament: { name: string | number; results: any; }) => {
        acc[tournament.name] = tournament.results;
        return acc;
      }, {});
      this.selectedTournamentData = this.tournamentData[this.selectedTournament] || [];
    });
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
    const resultData = {
      tournament: this.selectedTournament,
      playerName: this.playerName,
      score: this.score,
      rating: this.rating
    };

    if (!this.selectedTournament || !this.playerName || this.score === null || this.rating === null) {
      this.errorMessage = 'Please fill out all the fields.';
      return;
    }

    this.fcxService.submitResults(resultData).subscribe({
      next: () => {
        console.log('Results successfully submitted:', resultData);
        this.closeModal();
      },
      error: () => {
        this.errorMessage = 'Failed to submit results. Please try again.';
      }
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
    if (!this.selectedTournament) {
      this.errorMessage = 'No tournament selected to export.';
      return;
    }

    try {
      const csvContent = this.convertToCSV(this.selectedTournamentData);
      this.downloadCSV(`${this.selectedTournament}-results.csv`, csvContent);
      console.log('Exporting data for:', this.selectedTournament);
    } catch (error) {
      this.errorMessage = 'Failed to export data. Please try again.';
    }
  }

  convertToCSV(data: any[]): string {
    if (!data || !data.length) {
      throw new Error('No data available for export.');
    }

    const headers = ['Player Name', 'Score', 'Rating'];
    const rows = data.map(d => [d.playerName, d.score, d.rating].join(','));
    return [headers.join(','), ...rows].join('\n');
  }

  downloadCSV(filename: string, csvContent: string) {
    const blob = new Blob([csvContent], { type: 'text/csv' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = filename;
    link.click();
  }
}
