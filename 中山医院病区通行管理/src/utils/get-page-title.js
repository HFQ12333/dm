import defaultSettings from '@/settings'

const title = defaultSettings.title || '中山医院病区通行管理'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
