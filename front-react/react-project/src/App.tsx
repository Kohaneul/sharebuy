import './App.css'
import MainLayout from './components/layout/MainLayout';
import BoardPage from './pages/BoardPage';

function App() {
  // App.tsx는 이제 "어떤 화면을 보여줄지" 결정하는 라우터(Router) 역할만 합니다.
  // 지금은 페이지가 하나니까 바로 BoardPage를 보여주도록 설정할게요.

  return (
    <MainLayout>
      {/* MainLayout의 'children' 자리로 BoardPage가 들어갑니다.
         이렇게 하면 BoardPage 안에 일일이 TopNavBar를 안 넣어도
         MainLayout이 감싸고 있기 때문에 상단바가 자동으로 나옵니다.
      */}
      <BoardPage />
    </MainLayout>
  )
}

export default App