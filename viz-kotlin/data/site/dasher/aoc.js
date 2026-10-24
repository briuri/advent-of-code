/**
 * Shared Javascript for the AoC Fastest Times page.
 */
$(document).ready(function() {

	// Event handler for showing/hiding times in the Top X Overall section.
	$("span.bestTimeLink").click(
		function() {
			var place = $(this).attr("id").substring("bestTime".length);
			var oldDisplay = document.getElementById('details' + place).style.display;
			if (oldDisplay == 'block') {
				$('#details' + place).hide(150);
			}
			else {
				$('#details' + place).show(300);
			}
			// Add tiebreaker time glow effect for eligible players.
			if (!document.getElementById('bestTime' + place).classList.contains('ineligible')) {
				document.getElementById('bestTime' + place).style.color =
					(oldDisplay == 'block' ? '#ffffff' : '#888800');
			}
		});

	// Event handle for toggling between total/split times in the Top X Daily section.
	$("span.dailyLink").click(
		function() {
			oldDisplay = document.getElementById('dailySplit').style.display;
			if (oldDisplay == 'none') {
				$('span.dS').hide();
				$('span.dT').show();
			}
			else {
				$('span.dT').hide();
				$('span.dS').show();
			}
		});
	});