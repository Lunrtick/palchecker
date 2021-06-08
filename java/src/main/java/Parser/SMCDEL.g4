grammar SMCDEL;

VARS: 'VARS';
LAW: 'LAW';
OBS: 'OBS';
COMMA: ',';
LP: '(';
RP: ')';
LSB: '[';
RSB: ']';
LCB: '{';
RCB: '}';
OT: '<';
CT: '>';
EXC: '!';
AMP: '&';
PIPE: '|';
COLON: ':';
DOUBLEDASH: '--';
WHERE: 'WHERE?';
TRUE: 'TRUE?';
VALID: 'VALID?';
COMMENT: DOUBLEDASH ~[\r\n]* -> skip;
NEG: TILDE | N O T;
TILDE: '~';
CONJ: A N D | C O N J;
DISJ: O R | D I S J;
TOP: 'Top';
BOT: 'Bot';
KNOWS_THAT: 'knows that';
KNOWS_WHETHER: 'knows whether';
WS: [ \t\r\n]+ -> skip;

propositions: VARS props = variable_list;
binary_formula:
	left = VARIABLE AMP right = VARIABLE	# binconj
	| left = VARIABLE PIPE right = VARIABLE	# bindisj;

optionally_bracketed_formula:
	form = formula
	| LP form = formula RP;

formula_list:
	forms += optionally_bracketed_formula (
		COMMA forms += optionally_bracketed_formula
	)*;

variable_list: vs += VARIABLE (COMMA vs += VARIABLE)*;

formula:
	TOP																							# top
	| BOT																						# bot
	| binary_formula																			# bin
	| CONJ LP conjs = formula_list RP															# conj
	| DISJ LP disjs = formula_list RP															# disj
	| NEG negated = optionally_bracketed_formula												# neg
	| agent = VARIABLE KNOWS_THAT knows_that = optionally_bracketed_formula						# knowsthat
	| agent = VARIABLE KNOWS_WHETHER knows_whether = optionally_bracketed_formula				# knowswhether
	| OT EXC announced = optionally_bracketed_formula CT form = optionally_bracketed_formula	#
		pubAnnounceDiamond
	| LSB EXC announced = optionally_bracketed_formula RSB form = optionally_bracketed_formula #
		pubAnnounceBox
	| p = VARIABLE # prop;

state_law: LAW form = formula;

observation: agent = VARIABLE COLON vs = variable_list;

observations: OBS (obs += observation)+;

query:
	WHERE form = formula								# whereQuery
	| TRUE LCB props = variable_list RCB form = formula	# trueQuery
	| VALID form = formula								# validQuery;

queries: (q += query)+;

model:
	props = propositions law = state_law obs = observations qs = queries EOF;

VARIABLE: [a-zA-Z_0-9]+;

fragment A: ('a' | 'A');
fragment B: ('b' | 'B');
fragment C: ('c' | 'C');
fragment D: ('d' | 'D');
fragment E: ('e' | 'E');
fragment F: ('f' | 'F');
fragment G: ('g' | 'G');
fragment H: ('h' | 'H');
fragment I: ('i' | 'I');
fragment J: ('j' | 'J');
fragment K: ('k' | 'K');
fragment L: ('l' | 'L');
fragment M: ('m' | 'M');
fragment N: ('n' | 'N');
fragment O: ('o' | 'O');
fragment P: ('p' | 'P');
fragment Q: ('q' | 'Q');
fragment R: ('r' | 'R');
fragment S: ('s' | 'S');
fragment T: ('t' | 'T');
fragment U: ('u' | 'U');
fragment V: ('v' | 'V');
fragment W: ('w' | 'W');
fragment X: ('x' | 'X');
fragment Y: ('y' | 'Y');
fragment Z: ('z' | 'Z');