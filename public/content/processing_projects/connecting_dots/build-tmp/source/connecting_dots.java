import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class connecting_dots extends PApplet {

// based on https://www.instagram.com/p/BlNh5pYFRoq/

ArrayList<Blip> blips;
ArrayList<PVector> letter_points;

public void setup() {
	blips = new ArrayList<Blip>();
	
	
	
	// moving blips
	for(int i = 0; i < 450; i++) {
		Blip blippy = new Blip();
		blippy.setAcceleration(new PVector(random(-1, 1), random(-1, 1)));
		
		blips.add( blippy );
	}
	
	// Letter
	letter_points = new ArrayList<PVector>();
	
	letter_points.add(new PVector(49.92733436584472f,0));
	letter_points.add(new PVector(59.92321573769907f,0));
	letter_points.add(new PVector(69.92013312096475f,0));
	letter_points.add(new PVector(79.9231842791359f,0));
	letter_points.add(new PVector(89.91264299012488f,0));
	letter_points.add(new PVector(99.9218610338308f,0));
	letter_points.add(new PVector(109.92200226540939f,0));
	letter_points.add(new PVector(119.91173835754394f,0));
	letter_points.add(new PVector(129.91644609034296f,0));
	letter_points.add(new PVector(139.92563886554913f,0));
	letter_points.add(new PVector(149.91476028198144f,0));
	letter_points.add(new PVector(159.92544161540224f,0));
	letter_points.add(new PVector(169.91995214864613f,0));
	letter_points.add(new PVector(179.91935117816553f,0));
	letter_points.add(new PVector(189.9289579241909f,0));
	letter_points.add(new PVector(199.9267153977044f,0));
	letter_points.add(new PVector(209.9127637577057f,0));
	letter_points.add(new PVector(219.9292025943361f,-0.14125804342736956f));
	letter_points.add(new PVector(229.90687423609197f,-0.6015195295214653f));
	letter_points.add(new PVector(239.87767178991058f,-1.3811967854847171f));
	letter_points.add(new PVector(249.81175165929497f,-2.4786924113921125f));
	letter_points.add(new PVector(259.7234383513883f,-3.897913047694601f));
	letter_points.add(new PVector(269.56172396128545f,-5.634389804115635f));
	letter_points.add(new PVector(279.35222601473333f,-7.696560451984406f));
	letter_points.add(new PVector(289.05528732615176f,-10.08164179638494f));
	letter_points.add(new PVector(298.6746151929442f,-12.796709186621012f));
	letter_points.add(new PVector(308.21317220173f,-15.851772810853436f));
	letter_points.add(new PVector(317.6105645948562f,-19.2361542750994f));
	letter_points.add(new PVector(326.8904107585084f,-22.967773340307176f));
	letter_points.add(new PVector(336.0135060147573f,-27.042367462752736f));
	letter_points.add(new PVector(344.98130184700466f,-31.473007903817926f));
	letter_points.add(new PVector(353.76467751400816f,-36.25942852882487f));
	letter_points.add(new PVector(362.3352488250192f,-41.399833333902066f));
	letter_points.add(new PVector(370.68373256097266f,-46.90340741445834f));
	letter_points.add(new PVector(378.78211305706645f,-52.767589010274975f));
	letter_points.add(new PVector(386.6119659762583f,-58.995300042644956f));
	letter_points.add(new PVector(394.1385455231233f,-65.57450006123109f));
	letter_points.add(new PVector(401.3455058600346f,-72.50566059533972f));
	letter_points.add(new PVector(408.20928832843134f,-79.78092236311285f));
	letter_points.add(new PVector(414.7081046798661f,-87.39074789322156f));
	letter_points.add(new PVector(420.80878203319895f,-95.30566801618261f));
	letter_points.add(new PVector(426.5086112859101f,-103.52934207648039f));
	letter_points.add(new PVector(431.77479539967146f,-112.01731221120805f));
	letter_points.add(new PVector(436.60919061488323f,-120.77025496200885f));
	letter_points.add(new PVector(440.99310578942897f,-129.74742527356662f));
	letter_points.add(new PVector(444.9321475675686f,-138.94781598605275f));
	letter_points.add(new PVector(448.4151434623997f,-148.32580764928366f));
	letter_points.add(new PVector(451.4411848305525f,-157.84297831732488f));
	letter_points.add(new PVector(454.02635014329877f,-167.5071986947587f));
	letter_points.add(new PVector(456.1737904836032f,-177.27866323803028f));
	letter_points.add(new PVector(457.89360444371715f,-187.12771636727388f));
	letter_points.add(new PVector(459.20032662479355f,-197.03683820955825f));
	letter_points.add(new PVector(460.1093530792685f,-206.98871126763055f));
	letter_points.add(new PVector(460.6373474288722f,-216.98042627372817f));
	letter_points.add(new PVector(460.79973154923545f,-226.9805883121854f));
	letter_points.add(new PVector(460.631959217461f,-236.98146357986582f));
	letter_points.add(new PVector(460.1427113771629f,-246.96601511706046f));
	letter_points.add(new PVector(459.3136902544648f,-256.9301383714378f));
	letter_points.add(new PVector(458.12716134734455f,-266.85404080733656f));
	letter_points.add(new PVector(456.56121561028414f,-276.7447286437755f));
	letter_points.add(new PVector(454.6052289298539f,-286.54061042026404f));
	letter_points.add(new PVector(452.2349855240835f,-296.2606979032597f));
	letter_points.add(new PVector(449.44060304833806f,-305.85812786552884f));
	letter_points.add(new PVector(446.20838915987275f,-315.31298036793044f));
	letter_points.add(new PVector(442.52098788516344f,-324.61660082823624f));
	letter_points.add(new PVector(438.3812135359667f,-333.71578588807984f));
	letter_points.add(new PVector(433.7816087426418f,-342.59382138478367f));
	letter_points.add(new PVector(428.7281403366383f,-351.2146978910454f));
	letter_points.add(new PVector(423.2185004214465f,-359.56502851693193f));
	letter_points.add(new PVector(417.27238231641115f,-367.6052396773218f));
	letter_points.add(new PVector(410.900945725498f,-375.3184120324231f));
	letter_points.add(new PVector(404.13333923338797f,-382.6739862888144f));
	letter_points.add(new PVector(396.9880280733276f,-389.66210822847586f));
	letter_points.add(new PVector(389.4856291490399f,-396.27498897394435f));
	letter_points.add(new PVector(381.66610763022544f,-402.49373359086724f));
	letter_points.add(new PVector(373.5376775072822f,-408.329391127321f));
	letter_points.add(new PVector(365.14472868112136f,-413.76859826505216f));
	letter_points.add(new PVector(356.5065528632484f,-418.8190037002522f));
	letter_points.add(new PVector(347.6619426859077f,-423.4785134519823f));
	letter_points.add(new PVector(338.6314721080439f,-427.7571820396255f));
	letter_points.add(new PVector(329.435462165159f,-431.6649595706849f));
	letter_points.add(new PVector(320.0733286577463f,-435.2190887439251f));
	letter_points.add(new PVector(310.6058979675018f,-438.4137735296679f));
	letter_points.add(new PVector(301.03242863579874f,-441.2668143567073f));
	letter_points.add(new PVector(291.3508129066766f,-443.7929593172108f));
	letter_points.add(new PVector(281.60120479188856f,-445.9950471027196f));
	letter_points.add(new PVector(271.78081551675206f,-447.88630323746736f));
	letter_points.add(new PVector(261.908069418309f,-449.4740325246868f));
	letter_points.add(new PVector(251.97949785239996f,-450.7680684922636f));
	letter_points.add(new PVector(242.03539044463656f,-451.7719264347176f));
	letter_points.add(new PVector(232.07241411384064f,-452.4942410668617f));
	letter_points.add(new PVector(222.06569779550847f,-452.94192227977413f));
	letter_points.add(new PVector(212.07830279288697f,-453.1169113250845f));
	letter_points.add(new PVector(202.0790764138079f,-453.12f));
	letter_points.add(new PVector(192.0737629575864f,-453.11999999999995f));
	letter_points.add(new PVector(182.0721021998115f,-453.11999999999995f));
	letter_points.add(new PVector(172.08271752357484f,-453.12f));
	letter_points.add(new PVector(162.0767316461631f,-453.12f));
	letter_points.add(new PVector(152.0824276149529f,-453.12f));
	letter_points.add(new PVector(142.08474601998924f,-453.12f));
	letter_points.add(new PVector(132.07823622926867f,-453.12f));
	letter_points.add(new PVector(122.07544653655961f,-453.12f));
	letter_points.add(new PVector(112.07071779067832f,-453.12f));
	letter_points.add(new PVector(102.07520453453063f,-453.12f));
	letter_points.add(new PVector(92.0737840156816f,-453.12f));
	letter_points.add(new PVector(82.06589975347043f,-453.12f));
	letter_points.add(new PVector(72.0665994568239f,-453.12f));
	letter_points.add(new PVector(62.07644334856654f,-453.12f));
	letter_points.add(new PVector(52.0693833065033f,-453.12f));
	letter_points.add(new PVector(49.92f,-445.2791389436927f));
	letter_points.add(new PVector(49.92f,-435.26999009856604f));
	letter_points.add(new PVector(49.92f,-425.2663017847866f));
	letter_points.add(new PVector(49.92f,-415.2666083908081f));
	letter_points.add(new PVector(49.92f,-405.2711162120104f));
	letter_points.add(new PVector(49.919999999999995f,-395.27179205417633f));
	letter_points.add(new PVector(49.92f,-385.2833577346802f));
	letter_points.add(new PVector(49.92f,-375.2742145601132f));
	letter_points.add(new PVector(49.919999999999995f,-365.2838564876374f));
	letter_points.add(new PVector(49.92000000000001f,-355.27047181988326f));
	letter_points.add(new PVector(49.919999999999995f,-345.26998599939634f));
	letter_points.add(new PVector(49.92f,-335.2693743537202f));
	letter_points.add(new PVector(49.919999999999995f,-325.27975427996716f));
	letter_points.add(new PVector(49.919999999999995f,-315.2657492067774f));
	letter_points.add(new PVector(49.92f,-305.2806233239938f));
	letter_points.add(new PVector(49.92f,-295.2767124509811f));
	letter_points.add(new PVector(49.92f,-285.28010842678134f));
	letter_points.add(new PVector(49.92f,-275.2745618863746f));
	letter_points.add(new PVector(49.92f,-265.2818173757901f));
	letter_points.add(new PVector(49.92f,-255.2812812168617f));
	letter_points.add(new PVector(49.92f,-245.27123014540774f));
	letter_points.add(new PVector(49.92f,-235.2698104735371f));
	letter_points.add(new PVector(49.92f,-225.27399051358807f));
	letter_points.add(new PVector(49.919999999999995f,-215.2803965806961f));
	letter_points.add(new PVector(49.92f,-205.2650128643308f));
	letter_points.add(new PVector(49.92f,-195.26627962789163f));
	letter_points.add(new PVector(49.92000000000001f,-185.28168011963368f));
	letter_points.add(new PVector(49.92f,-175.26906897216105f));
	letter_points.add(new PVector(49.92000000000001f,-165.26946788283996f));
	letter_points.add(new PVector(49.92f,-155.2646536437061f));
	letter_points.add(new PVector(49.92f,-145.27870390675847f));
	letter_points.add(new PVector(49.92f,-135.27966932403083f));
	letter_points.add(new PVector(49.92f,-125.27931950821657f));
	letter_points.add(new PVector(49.92f,-115.27528862648717f));
	letter_points.add(new PVector(49.92f,-105.2720506231708f));
	letter_points.add(new PVector(49.92f,-95.26497749984264f));
	letter_points.add(new PVector(49.92f,-85.2782979747218f));
	letter_points.add(new PVector(49.92f,-75.26855129742063f));
	letter_points.add(new PVector(49.92f,-65.2828480605036f));
	letter_points.add(new PVector(49.92f,-55.278106711925936f));
	letter_points.add(new PVector(49.92f,-45.27310266725647f));
	letter_points.add(new PVector(49.92f,-35.272622764930134f));
	letter_points.add(new PVector(49.92f,-25.271457643689352f));
	letter_points.add(new PVector(49.92f,-15.270654489174484f));
	letter_points.add(new PVector(49.92f,-5.265196652328595f));
	letter_points.add(new PVector(130.56f,-70.40358419418337f));
	letter_points.add(new PVector(130.56f,-80.39093639403582f));
	letter_points.add(new PVector(130.56f,-90.40431877288502f));
	letter_points.add(new PVector(130.56f,-100.39822354335338f));
	letter_points.add(new PVector(130.55999999999997f,-110.39631825073157f));
	letter_points.add(new PVector(130.56f,-120.39422149157618f));
	letter_points.add(new PVector(130.56f,-130.40153915528208f));
	letter_points.add(new PVector(130.56f,-140.39982116785214f));
	letter_points.add(new PVector(130.56f,-150.3967320210999f));
	letter_points.add(new PVector(130.56f,-160.39643256753624f));
	letter_points.add(new PVector(130.56f,-170.40631200953794f));
	letter_points.add(new PVector(130.56f,-180.39870850575716f));
	letter_points.add(new PVector(130.56f,-190.39903327345127f));
	letter_points.add(new PVector(130.56f,-200.40100209867632f));
	letter_points.add(new PVector(130.56f,-210.40408779561986f));
	letter_points.add(new PVector(130.56f,-220.39740622799437f));
	letter_points.add(new PVector(130.56f,-230.40107184756235f));
	letter_points.add(new PVector(130.56f,-240.39315028283744f));
	letter_points.add(new PVector(130.56f,-250.40850112204498f));
	letter_points.add(new PVector(130.56f,-260.3946231106296f));
	letter_points.add(new PVector(130.56f,-270.39525275619235f));
	letter_points.add(new PVector(130.56f,-280.3921379871899f));
	letter_points.add(new PVector(130.56f,-290.4007697947509f));
	letter_points.add(new PVector(130.56f,-300.3986381835275f));
	letter_points.add(new PVector(130.56f,-310.4004499513976f));
	letter_points.add(new PVector(130.56f,-320.3967013532994f));
	letter_points.add(new PVector(130.56f,-330.4020682374248f));
	letter_points.add(new PVector(130.56f,-340.395263671875f));
	letter_points.add(new PVector(130.56f,-350.4021198969567f));
	letter_points.add(new PVector(130.56f,-360.3969212670112f));
	letter_points.add(new PVector(130.56f,-370.3991269345628f));
	letter_points.add(new PVector(130.56f,-380.396934135817f));
	letter_points.add(new PVector(136.957779817041f,-384));
	letter_points.add(new PVector(146.9559869094193f,-384));
	letter_points.add(new PVector(156.96249364367918f,-384));
	letter_points.add(new PVector(166.96257117971777f,-384));
	letter_points.add(new PVector(176.95770850539208f,-384));
	letter_points.add(new PVector(186.96315742492675f,-384));
	letter_points.add(new PVector(196.95849801063537f,-384));
	letter_points.add(new PVector(206.96742450027494f,-383.88482457936624f));
	letter_points.add(new PVector(216.95114951475205f,-383.4853350719636f));
	letter_points.add(new PVector(226.92592147820392f,-382.77563357772186f));
	letter_points.add(new PVector(236.86876600384713f,-381.7291694766283f));
	letter_points.add(new PVector(246.77220635541977f,-380.3150710541634f));
	letter_points.add(new PVector(256.6126611328125f,-378.50079345703125f));
	letter_points.add(new PVector(266.3504195429656f,-376.25461769898203f));
	letter_points.add(new PVector(275.974129654679f,-373.5352610629506f));
	letter_points.add(new PVector(285.4263035916723f,-370.31060297087765f));
	letter_points.add(new PVector(294.6909282530626f,-366.532359092791f));
	letter_points.add(new PVector(303.693888183379f,-362.1723805787867f));
	letter_points.add(new PVector(312.3635728789866f,-357.20845224894583f));
	letter_points.add(new PVector(320.65635453080756f,-351.60750350265766f));
	letter_points.add(new PVector(328.4711041223444f,-345.3855896605644f));
	letter_points.add(new PVector(335.753113616074f,-338.54670523750247f));
	letter_points.add(new PVector(342.4553918896616f,-331.10445754788816f));
	letter_points.add(new PVector(348.49420395699946f,-323.1480856103044f));
	letter_points.add(new PVector(353.86200310351563f,-314.7145688873681f));
	letter_points.add(new PVector(358.5441818138701f,-305.8783403526724f));
	letter_points.add(new PVector(362.5474151964926f,-296.7130109531088f));
	letter_points.add(new PVector(365.8896644970504f,-287.30298758986623f));
	letter_points.add(new PVector(368.618421085272f,-277.66871043750086f));
	letter_points.add(new PVector(370.7583778146925f,-267.9212186111581f));
	letter_points.add(new PVector(372.3674690972572f,-258.0403435267147f));
	letter_points.add(new PVector(373.48159180054444f,-248.09805158668198f));
	letter_points.add(new PVector(374.143927568479f,-238.12523785180994f));
	letter_points.add(new PVector(374.3954579226324f,-228.12509797344566f));
	letter_points.add(new PVector(374.24814102025204f,-218.13147769696383f));
	letter_points.add(new PVector(373.65307751477474f,-208.1572476740752f));
	letter_points.add(new PVector(372.5682424148967f,-198.21062478858673f));
	letter_points.add(new PVector(370.9534732818604f,-188.33855556488038f));
	letter_points.add(new PVector(368.7705928701148f,-178.58799882970183f));
	letter_points.add(new PVector(365.978316273611f,-168.98879585299656f));
	letter_points.add(new PVector(362.54095331534745f,-159.59021545030177f));
	letter_points.add(new PVector(358.44838588450307f,-150.4856048785937f));
	letter_points.add(new PVector(353.67211403830237f,-141.6997027135761f));
	letter_points.add(new PVector(348.22076009244284f,-133.311599459625f));
	letter_points.add(new PVector(342.11754042566986f,-125.38689745162498f));
	letter_points.add(new PVector(335.4102245969349f,-117.98866634774372f));
	letter_points.add(new PVector(328.1278240479366f,-111.1330370510032f));
	letter_points.add(new PVector(320.34139470281775f,-104.8581780631945f));
	letter_points.add(new PVector(312.121793960059f,-99.17834435098355f));
	letter_points.add(new PVector(303.5213297402859f,-94.08207813799382f));
	letter_points.add(new PVector(294.5946138638444f,-89.55439156140201f));
	letter_points.add(new PVector(285.4263035916723f,-85.58839513137006f));
	letter_points.add(new PVector(276.03228318579727f,-82.14380626398489f));
	letter_points.add(new PVector(266.4839259830676f,-79.20361898452975f));
	letter_points.add(new PVector(256.7934837255813f,-76.7317998676654f));
	letter_points.add(new PVector(247.00085037392796f,-74.70400220892276f));
	letter_points.add(new PVector(237.12984894765043f,-73.09407911026226f));
	letter_points.add(new PVector(227.20336733194065f,-71.87864551971667f));
	letter_points.add(new PVector(217.2439492154424f,-71.03676378011588f));
	letter_points.add(new PVector(207.25903438677864f,-70.54915312365061f));
	letter_points.add(new PVector(197.2577268218994f,-70.4f));
	letter_points.add(new PVector(187.25999999999996f,-70.4f));
	letter_points.add(new PVector(177.25551081759855f,-70.4f));
	letter_points.add(new PVector(167.25686895981434f,-70.4f));
	letter_points.add(new PVector(157.26401606038212f,-70.4f));
	letter_points.add(new PVector(147.25757847189905f,-70.4f));
	letter_points.add(new PVector(137.24907165527344f,-70.4f));
	/*
	letter_points.add(new PVector(33.45131402511597,0));
	letter_points.add(new PVector(43.45110405775701,0));
	letter_points.add(new PVector(53.438314098644256,0));
	letter_points.add(new PVector(63.44490055608114,0));
	letter_points.add(new PVector(73.4469048743263,0));
	letter_points.add(new PVector(83.44041902259904,0));
	letter_points.add(new PVector(93.43680837587323,0));
	letter_points.add(new PVector(103.45076204004621,0));
	letter_points.add(new PVector(113.44548598627715,0));
	letter_points.add(new PVector(123.45309607391115,0));
	letter_points.add(new PVector(133.44259023396725,0));
	letter_points.add(new PVector(143.44541663444488,-0.013511578385863686));
	letter_points.add(new PVector(153.43695496417692,-0.3665583554937796));
	letter_points.add(new PVector(163.39664844615837,-1.1953810337611008));
	letter_points.add(new PVector(173.3117718160961,-2.50054506160277));
	letter_points.add(new PVector(183.15613885289363,-4.285144817777351));
	letter_points.add(new PVector(192.88964000490915,-6.551303705440834));
	letter_points.add(new PVector(202.50127072916115,-9.309991022469477));
	letter_points.add(new PVector(211.9507333603398,-12.567597588031367));
	letter_points.add(new PVector(221.2249087156468,-16.34247393382215));
	letter_points.add(new PVector(230.2547528280735,-20.632120671272276));
	letter_points.add(new PVector(239.01234983787344,-25.45051406288534));
	letter_points.add(new PVector(247.45549869382546,-30.80646872149937));
	letter_points.add(new PVector(255.52998149184745,-36.69974131524768));
	letter_points.add(new PVector(263.18376097707005,-43.12691029200987));
	letter_points.add(new PVector(270.36758154384427,-50.08128580598117));
	letter_points.add(new PVector(277.02598328588203,-57.541270123398675));
	letter_points.add(new PVector(283.1106530199288,-65.47856163687162));
	letter_points.add(new PVector(288.5735389810086,-73.84489115430961));
	letter_points.add(new PVector(293.39333302020555,-82.6099993990954));
	letter_points.add(new PVector(297.54293476133915,-91.710546830431));
	letter_points.add(new PVector(301.0147473435047,-101.08906625357531));
	letter_points.add(new PVector(303.8123016337961,-110.68181045562942));
	letter_points.add(new PVector(305.9536373596847,-120.4362287078619));
	letter_points.add(new PVector(307.4684551893941,-130.33300332138353));
	letter_points.add(new PVector(308.3807318783014,-140.2816693043243));
	letter_points.add(new PVector(308.7300044025154,-150.28053221173226));
	letter_points.add(new PVector(308.56917406005516,-160.2758899286994));
	letter_points.add(new PVector(307.92118804245047,-170.25570644394395));
	letter_points.add(new PVector(306.7469119003843,-180.18265846358025));
	letter_points.add(new PVector(305.0046839737033,-190.03019573662647));
	letter_points.add(new PVector(302.65609014110777,-199.75133074916468));
	letter_points.add(new PVector(299.6711887812377,-209.28323071112996));
	letter_points.add(new PVector(296.01420327986125,-218.59661750598306));
	letter_points.add(new PVector(291.6790546959246,-227.60318921249655));
	letter_points.add(new PVector(286.6580986769511,-236.25245609989187));
	letter_points.add(new PVector(280.97866882713834,-244.46240757919045));
	letter_points.add(new PVector(274.64593067320817,-252.21147175512124));
	letter_points.add(new PVector(267.72194217458144,-259.42816547023693));
	letter_points.add(new PVector(260.26407056490405,-266.07891917523887));
	letter_points.add(new PVector(252.31613896608116,-272.1585242394022));
	letter_points.add(new PVector(243.9633693176401,-277.64315275399144));
	letter_points.add(new PVector(235.24863072427243,-282.5498012803381));
	letter_points.add(new PVector(226.24155233709158,-286.8835957327932));
	letter_points.add(new PVector(216.9872263905339,-290.66620302330404));
	letter_points.add(new PVector(207.52999471707344,-293.91898146886825));
	letter_points.add(new PVector(197.9134541810727,-296.6629829459241));
	letter_points.add(new PVector(188.18046209669808,-298.91895503842295));
	letter_points.add(new PVector(178.3439270139774,-300.7119811537661));
	letter_points.add(new PVector(168.4302008769103,-302.05999414211135));
	letter_points.add(new PVector(158.4803011176435,-302.9773813425401));
	letter_points.add(new PVector(148.49177823136972,-303.47998455930053));
	letter_points.add(new PVector(138.49450376837999,-303.5904));
	letter_points.add(new PVector(128.49173766399196,-303.5904));
	letter_points.add(new PVector(118.49628255562784,-303.59040000000005));
	letter_points.add(new PVector(108.49093295469284,-303.59040000000005));
	letter_points.add(new PVector(98.49881027947981,-303.5904));
	letter_points.add(new PVector(88.48256683861335,-303.5904));
	letter_points.add(new PVector(78.4865536780331,-303.5904));
	letter_points.add(new PVector(68.4833580246479,-303.5904));
	letter_points.add(new PVector(58.49385976113423,-303.5904));
	letter_points.add(new PVector(48.496688203100305,-303.5904));
	letter_points.add(new PVector(38.49320162553788,-303.5904));
	letter_points.add(new PVector(33.4464,-298.6367194276392));
	letter_points.add(new PVector(33.446400000000004,-288.63885655336975));
	letter_points.add(new PVector(33.446400000000004,-278.6334615082218));
	letter_points.add(new PVector(33.446400000000004,-268.63509121178004));
	letter_points.add(new PVector(33.446400000000004,-258.63124046289323));
	letter_points.add(new PVector(33.446400000000004,-248.6344561775805));
	letter_points.add(new PVector(33.446400000000004,-238.62564155546102));
	letter_points.add(new PVector(33.446400000000004,-228.64298156211152));
	letter_points.add(new PVector(33.44640000000001,-218.63247959961492));
	letter_points.add(new PVector(33.446400000000004,-208.625403176389));
	letter_points.add(new PVector(33.446400000000004,-198.63353465671491));
	letter_points.add(new PVector(33.446400000000004,-188.62653501341924));
	letter_points.add(new PVector(33.446400000000004,-178.62989447988502));
	letter_points.add(new PVector(33.446400000000004,-168.63560423746986));
	letter_points.add(new PVector(33.44640000000001,-158.63059944281645));
	letter_points.add(new PVector(33.4464,-148.62682797491652));
	letter_points.add(new PVector(33.446400000000004,-138.63529475686988));
	letter_points.add(new PVector(33.446400000000004,-128.6396353717111));
	letter_points.add(new PVector(33.446400000000004,-118.62593061380787));
	letter_points.add(new PVector(33.446400000000004,-108.63850680386646));
	letter_points.add(new PVector(33.446400000000004,-98.63264896778044));
	letter_points.add(new PVector(33.446400000000004,-88.62702935600281));
	letter_points.add(new PVector(33.446400000000004,-78.62628062691735));
	letter_points.add(new PVector(33.446400000000004,-68.62858321705396));
	letter_points.add(new PVector(33.446400000000004,-58.625204490885885));
	letter_points.add(new PVector(33.446400000000004,-48.62970238887952));
	letter_points.add(new PVector(33.446400000000004,-38.64254960471028));
	letter_points.add(new PVector(33.446400000000004,-28.629371826676277));
	letter_points.add(new PVector(33.446400000000004,-18.633490753150486));
	letter_points.add(new PVector(33.446400000000004,-8.637114916444569));
	letter_points.add(new PVector(87.47520000000002,-47.17759311676026));
	letter_points.add(new PVector(87.47519999999999,-57.16078756082058));
	letter_points.add(new PVector(87.47519999999999,-67.1717248946149));
	letter_points.add(new PVector(87.47519999999999,-77.16027885596453));
	letter_points.add(new PVector(87.4752,-87.1675348632183));
	letter_points.add(new PVector(87.47519999999999,-97.16144815525227));
	letter_points.add(new PVector(87.4752,-107.16674242846855));
	letter_points.add(new PVector(87.4752,-117.16875361477143));
	letter_points.add(new PVector(87.47519999999999,-127.16423881315511));
	letter_points.add(new PVector(87.4752,-137.1697024286418));
	letter_points.add(new PVector(87.4752,-147.1665838572361));
	letter_points.add(new PVector(87.4752,-157.16611444182826));
	letter_points.add(new PVector(87.4752,-167.16393134450888));
	letter_points.add(new PVector(87.4752,-177.17129918577336));
	letter_points.add(new PVector(87.4752,-187.16972101370993));
	letter_points.add(new PVector(87.4752,-197.17582044899814));
	letter_points.add(new PVector(87.4752,-207.16986592463826));
	letter_points.add(new PVector(87.4752,-217.1720105558748));
	letter_points.add(new PVector(87.4752,-227.17607323455815));
	letter_points.add(new PVector(87.4752,-237.16929155625402));
	letter_points.add(new PVector(87.4752,-247.1635787099258));
	letter_points.add(new PVector(87.4752,-257.1632513933182));
	letter_points.add(new PVector(97.35590787191391,-257.28000000000003));
	letter_points.add(new PVector(107.35444227905273,-257.28000000000003));
	letter_points.add(new PVector(117.36593578359839,-257.28000000000003));
	letter_points.add(new PVector(127.3563109398678,-257.28000000000003));
	letter_points.add(new PVector(137.37341622818616,-257.23208030272195));
	letter_points.add(new PVector(147.350475110502,-256.8159795861089));
	letter_points.add(new PVector(157.32239786247854,-255.92347966983598));
	letter_points.add(new PVector(167.20392706781988,-254.4960363873621));
	letter_points.add(new PVector(176.99070396273584,-252.45639762058187));
	letter_points.add(new PVector(186.61188656223845,-249.7239621454194));
	letter_points.add(new PVector(195.9716651273081,-246.21726091226571));
	letter_points.add(new PVector(204.96977583111152,-241.85150763663646));
	letter_points.add(new PVector(213.45212147753185,-236.57320920073818));
	letter_points.add(new PVector(221.27632500764116,-230.34979839543024));
	letter_points.add(new PVector(228.27657675016712,-223.22066187739807));
	letter_points.add(new PVector(234.33537829468764,-215.2710621195976));
	letter_points.add(new PVector(239.3820582214237,-206.64101839866845));
	letter_points.add(new PVector(243.40592703561083,-197.5046113083206));
	letter_points.add(new PVector(246.4685991184904,-187.98463085085086));
	letter_points.add(new PVector(248.64473414304985,-178.226422290239));
	letter_points.add(new PVector(250.03129848264757,-168.32936400869306));
	letter_points.add(new PVector(250.72574961212186,-158.3550318954385));
	letter_points.add(new PVector(250.810538560389,-148.35150732117071));
	letter_points.add(new PVector(250.25051381434912,-138.3586424788394));
	letter_points.add(new PVector(248.9525948862349,-128.45402749266427));
	letter_points.add(new PVector(246.82377027032882,-118.68744548520381));
	letter_points.add(new PVector(243.77986470231863,-109.16721383524313));
	letter_points.add(new PVector(239.75666861044317,-100.0199772454165));
	letter_points.add(new PVector(234.72131334854367,-91.37930210265517));
	letter_points.add(new PVector(228.70929425950763,-83.40583917211083));
	letter_points.add(new PVector(221.78751086800347,-76.2016763777365));
	letter_points.add(new PVector(214.07476537344178,-69.8450302691314));
	letter_points.add(new PVector(205.70709340791072,-64.36296393308206));
	letter_points.add(new PVector(196.84284694167556,-59.75233082366211));
	letter_points.add(new PVector(187.59751367586853,-55.96268054841161));
	letter_points.add(new PVector(178.06691345538889,-52.93337842699205));
	letter_points.add(new PVector(168.34358392736846,-50.60557547100876));
	letter_points.add(new PVector(158.4756613803413,-48.916420233294495));
	letter_points.add(new PVector(148.5488029826626,-47.817565621260556));
	letter_points.add(new PVector(138.56535748282673,-47.25873281766772));
	letter_points.add(new PVector(128.5627497898087,-47.168));
	letter_points.add(new PVector(118.5616646986127,-47.168));
	letter_points.add(new PVector(108.5700412817493,-47.168));
	letter_points.add(new PVector(98.56140513521602,-47.168));
	letter_points.add(new PVector(88.56584997285604,-47.16799999999999));
	*/
	for(int i = 0; i < letter_points.size(); i++) {
		PVector letter_pt = letter_points.get(i);
		
		Blip blippy = new Blip(
			(letter_pt.x + (width / 2) - 240),
			(letter_pt.y + (height / 2) + 226)
		);
		blippy.stopAcceleration();
		
		blips.add( blippy );
	}
}

public void draw() {
	background(10);
	
	for(int i = 0; i < blips.size(); i++) {
		Blip blip = blips.get(i);
		
		blip.update();
	}
	
	for(int i = 0; i < blips.size(); i++) {
		Blip blip = blips.get(i);
		
		blip.countNeighbors(blips);
		blip.drawLines();
	}
	
	//for(int i = 0; i < blips.size(); i++) {
	//	Blip blip = blips.get(i);
	//	
	//	blip.draw();
	//}
	
	if(frameCount == 50) { save("preview.png"); }
}
class Blip {
	PVector location;
	PVector velocity;
	PVector acceleration;
	String uniq_id;
	float maxDist = 36.0f;
	boolean invisible = false;
	
	int num_neighbors;
	ArrayList<PVector> neighbors;
	
	Blip() {
		location = new PVector(random(0, width), random(0, height));
		
		init();
	}
	
	Blip(float x_, float y_) {
		location = new PVector(x_, y_);
		
		init();
	}
	
	public void init() {
		velocity = new PVector(0, 0);
		acceleration = PVector.random2D();
		acceleration.mult(0.3f);
		
		genRandID();
		
		neighbors = new ArrayList<PVector>();
	}
	
	public void genRandID() {
		uniq_id = "" + PApplet.parseChar( PApplet.parseInt( random(33,126) ) ) + PApplet.parseChar( PApplet.parseInt( random(33,126) ) ) + PApplet.parseChar( PApplet.parseInt( random(33,126) ) ) + PApplet.parseChar( PApplet.parseInt( random(33,126) ) ) + PApplet.parseChar( PApplet.parseInt( random(33,126) ) ) + PApplet.parseChar( PApplet.parseInt( random(33,126) ) );
	}
	
	public void stopAcceleration() {
		acceleration.set(0, 0);
		invisible = true;
	}
	
	public void setAcceleration(PVector vel_) {
		acceleration = vel_.copy();
	}
	
	public void setAcceleration(float x_, float y_) {
		acceleration.set(x_, y_);
	}
	
	public void update() {
		// movement
		velocity.add(acceleration);
		velocity.limit(1);
		
		location.add(velocity);
		
		if(location.x > width) {
			location.x -= width;
		} else if(location.x < 0) {
			location.x += width;
		}
		
		if(location.y > height) {
			location.y -= height;
		} else if(location.y < 0) {
			location.y += height;
		}
	}
	
	public void countNeighbors(ArrayList<Blip> neighbors_) {
		for(int i = 0; i < neighbors_.size(); i++) {
			Blip other_blip = neighbors_.get(i);
			
			if(uniq_id.equals(other_blip.uniq_id)) {
				continue;
			}
			
			float distance = dist(
				location.x,
				location.y,
				other_blip.location.x,
				other_blip.location.y
			);
			
			if(distance <= maxDist) {
				PVector other_loc = other_blip.location.copy();
				
				neighbors.add(other_loc);
			}
		}
	}
	
	public void drawLines() {
		pushMatrix();
		pushStyle();
		
		noFill();
		
		if(invisible) {
			stroke(color(225,255,23));
		} else {
			stroke(color(18,123,229));
		}
		
		num_neighbors = 0;
		
		// lines
		if(neighbors.size() > 0) {
			for(int i = (neighbors.size() - 1); i >= 0; i--) {
				PVector other_loc = neighbors.get(i);
				
				line(
					location.x,
					location.y,
					other_loc.x,
					other_loc.y
				);
				
				neighbors.remove(i);
				
				num_neighbors++;
			}
		}
		
		popStyle();
		popMatrix();
	}
	
	public void draw() {
		if(invisible) {
			return;
		}
		
		// dot
		pushMatrix();
		pushStyle();
		
		float radius = (1.0f + num_neighbors);
		
		fill(color(225,255,23));
		noStroke();
		
		ellipse(location.x, location.y, radius, radius);
		
		popStyle();
		popMatrix();
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "connecting_dots" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
