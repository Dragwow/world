<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<title>Мировое господство</title>
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://unpkg.com/react@18/umd/react.development.js"></script>
<script src="https://unpkg.com/react-dom@18/umd/react-dom.development.js"></script>
<script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
</head>

<body class="bg-gray-100">
<div id="root"></div>

<script type="text/babel">

const {useState} = React;
const ADMIN_PASSWORD = "host123";

const countriesData = [
 {name:"США", cities:["Нью-Йорк","Лос-Анджелес","Чикаго","Хьюстон"]},
 {name:"Германия", cities:["Берлин","Мюнхен","Гамбург","Франкфурт"]},
 {name:"Китай", cities:["Пекин","Шанхай","Гуанчжоу","Шэньчжэнь"]},
 {name:"Иран", cities:["Тегеран","Шираз","Исфахан","Тебриз"]},
 {name:"Сев. Корея", cities:["Пхеньян","Кэсон","Расон","Вонсан"]},
 {name:"Израиль", cities:["Тель-Авив","Иерусалим","Хайфа","Эйлат"]}
];

const makeCity = name => ({
  name,
  economy: 0,
  ecology: 0,
  shield: 0,
  baseIncome: 100,
  income: 100,
  life: 50,
  destroyed:false
});

function recalcCountry(c){
  c.cities.forEach(city=>{
    if(city.destroyed){
      city.income=0;
      city.life=0;
      return;
    }

    city.income = Math.round(
      city.baseIncome * (1 + city.economy/100)
    );

    city.life = Math.max(0,
      city.economy +
      city.ecology +
      city.shield*5 -
      c.sanctions
    );
  });

  c.budget = c.cities.reduce((s,ct)=>s+ct.income,0);
}

function App(){
  const [view,setView]=useState("login");
  const [pass,setPass]=useState("");
  const [player,setPlayer]=useState(null);

  const [countries,setCountries]=useState(
    countriesData.map(c=>({
      name:c.name,
      nukes:0,
      sanctions:0,
      budget:0,
      cities:c.cities.map(makeCity)
    }))
  );

  const update = () =>{
    const copy=[...countries];
    copy.forEach(recalcCountry);
    setCountries(copy);
  };

  const addStat=(ci,i,key,val)=>{
    const copy=[...countries];
    copy[ci].cities[i][key]+=val;
    update();
  };

  const addNukes=(ci,val)=>{
    const copy=[...countries];
    copy[ci].nukes+=val;
    setCountries(copy);
  };

  const nuke=(from,to,i)=>{
    const copy=[...countries];
    if(copy[from].nukes<=0) return;
    const city=copy[to].cities[i];
    if(city.shield>0) city.shield--;
    else city.destroyed=true;
    copy[from].nukes--;
    update();
  };

  if(view==="login") return(
    <div className="p-10 max-w-6xl mx-auto text-center">
      <h1 className="text-4xl font-bold mb-6">🌍 Мировое господство</h1>

      <input type="password" className="border px-4 py-2"
       placeholder="Пароль ведущего"
       onChange={e=>setPass(e.target.value)}/>

      <button className="bg-blue-600 text-white px-6 py-2 ml-2 rounded"
        onClick={()=>pass===ADMIN_PASSWORD?setView("admin"):alert("Неверно")}>
        Войти ведущим
      </button>

      <div className="grid grid-cols-2 md:grid-cols-3 gap-4 mt-8">
        {countries.map((c,i)=>(
          <button key={i}
            className="bg-green-600 text-white p-4 rounded"
            onClick={()=>{setPlayer(i);setView("player")}}>
            {c.name}
          </button>
        ))}
      </div>
    </div>
  );

  if(view==="admin") return(
    <div className="p-6 space-y-6">
      <button className="border px-3 py-1" onClick={()=>setView("login")}>Выход</button>

      {countries.map((c,ci)=>(
        <div key={ci} className="bg-white p-4 rounded shadow">

          <h2 className="text-xl font-bold">{c.name}</h2>

          <div className="flex gap-4 my-2">
            <div>💰 Доход: {c.budget}</div>
            <div>💣 Ядер: {c.nukes}</div>
            <button onClick={()=>addNukes(ci,1)} className="bg-red-500 text-white px-2">+1 боеголовка</button>
          </div>

          <div>
            Санкции:
            <input type="number" className="border w-20 ml-2"
             value={c.sanctions}
             onChange={e=>{c.sanctions=+e.target.value;update()}}/>
          </div>

          <div className="grid md:grid-cols-2 gap-3 mt-3">

            {c.cities.map((city,i)=>(
              <div key={i} className="border p-3 rounded">

                <b>{city.name} {city.destroyed && "💥"}</b>

                <div>Экономика: {city.economy}%</div>
                <div>Экология: {city.ecology}%</div>
                <div>Щит: {city.shield}</div>
                <div>Доход: {city.income}</div>
                <div>Жизнь: {city.life}%</div>

                <div className="flex flex-wrap gap-1 mt-2">
                  <button onClick={()=>addStat(ci,i,"economy",20)} className="bg-green-500 text-white px-2">+эконо</button>
                  <button onClick={()=>addStat(ci,i,"ecology",15)} className="bg-blue-500 text-white px-2">+экол</button>
                  <button onClick={()=>addStat(ci,i,"shield",1)} className="bg-gray-700 text-white px-2">+щит</button>
                </div>

              </div>
            ))}
          </div>

          <div className="flex flex-wrap gap-2 mt-3">
            {countries.map((t,ti)=>ti!==ci && t.cities.map((_,i)=>(
              <button key={ti+i} className="bg-red-600 text-white px-2"
               onClick={()=>nuke(ci,ti,i)}>
                💣 {t.name} {i+1}
              </button>
            )))}
          </div>

        </div>
      ))}
    </div>
  );

  const me=countries[player];

  return(
    <div className="p-6 grid md:grid-cols-2 gap-6">

      <div className="bg-white p-4 rounded shadow">
        <h2 className="text-2xl font-bold">{me.name}</h2>
        <p>💰 Доход: {me.budget}</p>
        <p>📉 Санкции: {me.sanctions}%</p>

        {me.cities.map((city,i)=>(
          <div key={i} className="border p-2 mt-2 rounded">
            <b>{city.name}</b>
            <div>Экономика {city.economy}%</div>
            <div>Экология {city.ecology}%</div>
            <div>Щит {city.shield}</div>
            <div>Доход {city.income}</div>
            <div>Жизнь {city.life}%</div>
          </div>
        ))}
      </div>

      <div className="space-y-3">
        {countries.map((c,i)=>i!==player &&(
          <div key={i} className="bg-white p-3 rounded shadow">
            <b>{c.name}</b>
            <div>Санкции: {c.sanctions}%</div>
            <div>Доход: {c.budget}</div>
          </div>
        ))}
      </div>

    </div>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(<App/>);

</script>
</body>
</html>
